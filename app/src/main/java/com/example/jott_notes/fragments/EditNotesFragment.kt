package com.example.jott_notes.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialContainerTransform
import com.thebluealliance.spectrum.SpectrumPalette
import kotlinx.android.synthetic.main.fragment_edit_notes.*
import kotlinx.android.synthetic.main.fragment_edit_notes.notes_desc
import kotlinx.android.synthetic.main.fragment_notes_create.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*


class EditNotesFragment : Fragment(),EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks  {

    private val addednotes by navArgs<EditNotesFragmentArgs>()
    //var priorityColor: String = "0"

    private var color : Int = 0
    private lateinit var tts : TextToSpeech
    private lateinit var binding: FragmentNotesCreateBinding
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    private var imagePath = ""
    private val viewModel: NotesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            scrimColor = Color.TRANSPARENT
//            isHoldAtEndEnabled = true
            isElevationShadowEnabled = false
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            duration = 300L
        }
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        arguments?.let {

        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Edit Note"

        binding = FragmentNotesCreateBinding.inflate(layoutInflater,container,false)
        //setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val uri = Uri.parse(imagePath)


        //val imageView: ImageView = view.findViewById<ImageView>(R.id.imagePage2)!!

        binding.notesTitle.setText(addednotes.dataTransfer?.title)
        addednotes.dataTransfer?.notesdesc?.let { binding.notesDesc.renderMD(it) }
        binding.date.setText("Modified On\n${addednotes.dataTransfer?.date}")



        if (addednotes.dataTransfer?.image != null && addednotes.dataTransfer?.image != "")
        {
            //imagePath = addednotes.dataTransfer!!.image
            binding.imagePage.setImageURI(addednotes.dataTransfer!!.image.toUri())
            binding.imagePage.visibility = View.VISIBLE

        }else{
            binding.imagePage.visibility = View.GONE

        }


        addednotes.dataTransfer?.color?.let {
            binding.noteContentFragmentParent.setBackgroundColor(
                it
            )

        }
//        requireActivity().window.statusBarColor = color
//        (activity as AppCompatActivity?)!!.supportActionBar?.setBackgroundDrawable(
//            addednotes.dataTransfer?.let {
//                ColorDrawable(
//                    it.color
//                )
//            }
//        )
        addednotes.dataTransfer?.color?.let { binding.toolbarInCreate.setBackgroundColor(it) }


        binding.SaveNoteButtonFAB.setOnClickListener {
            updateNotes(it)
        }


        try {
            binding.notesDesc.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.bottomBar.visibility = View.VISIBLE
                    binding.notesDesc.setStylesBar(binding.styleBar)
                } else {
                    binding.bottomBar.visibility = View.GONE
                }
            }
        } catch (e: Throwable) {
            Log.d("TAG", "FROM EDIT NOTES")
        }

        ViewCompat.setTransitionName(
            binding.noteContentFragmentParent,
            "recyclerView_${addednotes.dataTransfer?.id}"
        )

        //binding.frameLayoutEditText.background = addednotes.dataTransfer?.color?.toDrawable()

        val bottomSheet = binding.MoreOptions
        val shareButton = binding.share

        binding.backButtonCreate.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        bottomSheet.setOnClickListener {
            bottomSheetFunctions()
        }

        shareButton.setOnClickListener {
            shareFunction()



        }




    }

    private fun shareFunction() {
        val share = Intent()
        share.action = Intent.ACTION_SEND
        share.type = "text/plain"
        share.putExtra(Intent.EXTRA_TEXT,binding.notesDesc.getMD())
        context?.startActivity(Intent.createChooser(share,getString(R.string.app_name)))
    }

    private fun bottomSheetFunctions() {
        val bottomSheetMoreOptions = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        bottomSheetMoreOptions.setContentView(R.layout.fragment_notes_page_bottom_sheet)
        bottomSheetMoreOptions.show()

        val iconSwitch = bottomSheetMoreOptions.findViewById<SwitchCompat>(R.id.switch_button)

        bottomSheetMoreOptions.findViewById<LinearLayout>(R.id.textToSpeech)


        val colorPick = bottomSheetMoreOptions.findViewById<SpectrumPalette>(R.id.colorPicker)

        colorPick?.apply {
            setSelectedColor(color)
            setOnColorSelectedListener { value ->
                color = value
                binding.apply {
                    noteContentFragmentParent.setBackgroundColor(color)
                    bottomBar.setBackgroundColor(color)
                }

            }
        }

        iconSwitch?.setOnClickListener {



            if (iconSwitch.isChecked){
                tts = TextToSpeech(context?.applicationContext) {
                    if (it == TextToSpeech.SUCCESS) {
                        tts.language = Locale.getDefault()
                        tts.setSpeechRate(0.70f)
                        tts.speak(notes_desc.text.toString(), TextToSpeech.QUEUE_ADD, null)
                    }
                }
            }
            else{
                stopTextToSpeech()

            }
        }

        val addImage = bottomSheetMoreOptions.findViewById<LinearLayout>(R.id.Add_Image)
        addImage?.setOnClickListener {
            readStorageTask()
            bottomSheetMoreOptions.dismiss()
        }
    }


    private fun updateNotes(it: View?) {
        val notesTitle = binding.notesTitle.text.toString()
        val notesDesc = binding.notesDesc.getMD()
        val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
        val currentDate = sdf.format(Date())
       // val imagenew = getUriOfImage(binding.im)


        val data =
            Notes(
                addednotes.dataTransfer?.id,
                title = notesTitle,
                notesdesc = notesDesc,
                date = currentDate.toString(),
                color = color,
                image = imagePath

            )

       Log.e("@@@@@","updateNotes: Title : $notesTitle SubTile : $notesDesc")

        viewModel.updateNotes(data)

        Toast.makeText(
            activity?.applicationContext,
            "Note Updated Successfully",
            Toast.LENGTH_SHORT
        ).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }




    private fun stopTextToSpeech() {

            Toast.makeText(context,"Reader Stopped",Toast.LENGTH_SHORT).show()
            if (tts.isSpeaking){
                tts.stop()
                tts.shutdown()
            }
            else
            {
                Toast.makeText(context,"Reader Never Started",Toast.LENGTH_SHORT).show()
            }

    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

//    private fun hasWriteStoragePermission():Boolean{
//        return EasyPermissions.hasPermissions(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    }

    private fun readStorageTask() {
        if (hasReadStoragePermission()) {

            pickImage()
            Toast.makeText(requireContext(), "Permission Granted, Thank You!", Toast.LENGTH_SHORT)
                .show()


        } else {
            EasyPermissions.requestPermissions(
                requireActivity(),
                "Jott Notes needs storage permission",
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE

            )
        }

    }

    private fun pickImage() {
//        var intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//
//        intent.setType("image/*")
//        if (intent.resolveActivity(requireActivity().packageManager) != null){
//            startActivityForResult(intent,REQUEST_CODE_IMAGE)
//        }

        var intent = Intent()
        intent.type = "image/*"
        intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        //intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Image"), REQUEST_CODE_IMAGE)
    }

    private fun getUriOfImage(contentUri: Uri): String? {
        var filePath: String? = null
        var cursor = requireActivity().contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null){
            filePath = contentUri.path
        }else{
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath= cursor.getString(index)
            cursor.close()
        }
        return filePath

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var selectedImageUrl = data.data
                if (selectedImageUrl != null) {
                    try {

                        var inputStream =
                            requireActivity().contentResolver.openInputStream(selectedImageUrl)
                        var bitmap = BitmapFactory.decodeStream(inputStream)
                        imagePage2.setImageBitmap(bitmap)
                        imagePage2.visibility = View.VISIBLE
                        imagePath = getUriOfImage(selectedImageUrl)!!

                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            requireActivity()
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }



    companion object {

        @JvmStatic
        fun newInstance() =
            EditNotesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}