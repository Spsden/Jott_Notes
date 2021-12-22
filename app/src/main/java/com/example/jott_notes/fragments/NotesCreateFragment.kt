package com.example.jott_notes.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.jott_notes.R
import com.example.jott_notes.databinding.FragmentNotesCreateBinding
import com.example.jott_notes.mvvmstuff.Viewmodel.NotesViewModel
import com.example.jott_notes.mvvmstuff.entity.Notes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thebluealliance.spectrum.SpectrumPalette
import kotlinx.android.synthetic.main.fragment_notes_create.*
import kotlinx.android.synthetic.main.fragment_notes_create.view.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class NotesCreateFragment : Fragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {

    private lateinit var binding: FragmentNotesCreateBinding
    private var color: Int = 0
    private val viewModel: NotesViewModel by viewModels()
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    private var imagePath = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                tts.stop()
//                tts.shutdown()
                view?.let { Navigation.findNavController(it).popBackStack() }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        // (activity as AppCompatActivity?)!!.supportActionBar?.title = "Add Note"


        binding = FragmentNotesCreateBinding.inflate(
            layoutInflater, container, false
        )
        setHasOptionsMenu(true)


        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.SaveNoteButtonFAB.setOnClickListener {

            createNotes(it)

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
            Log.d("TAG", "FROM CREATE NOTES")
        }

        color = resources.getColor(R.color.bg_sheet)

        val bottomSheet = binding.MoreOptions
        binding.share

        binding.backButtonCreate.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }


        bottomSheet.setOnClickListener {
            bottomSheetMenuOptions()
        }




    }

    private fun bottomSheetMenuOptions() {

        val bottomSheetMoreOptions =
            BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetMoreOptions.setContentView(R.layout.fragment_notes_page_bottom_sheet)
        bottomSheetMoreOptions.show()


        val colorPick = bottomSheetMoreOptions.findViewById<SpectrumPalette>(R.id.colorPicker)

        colorPick?.apply {
            setSelectedColor(color)
            setOnColorSelectedListener { value ->
                color = value
                binding.apply {
                    noteContentFragmentParent.setBackgroundColor(color)
                    bottomBar.setBackgroundColor(color)
                    style_bar.setBackgroundColor(color)
                    requireActivity().window.statusBarColor = color
                }

            }
        }

        val addImage = bottomSheetMoreOptions.findViewById<LinearLayout>(R.id.Add_Image)
        addImage?.setOnClickListener {
            readStorageTask()
            bottomSheetMoreOptions.dismiss()
        }


    }

    private fun createNotes(it: View?) {

        if (binding.notesTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Atleast Title Required", Toast.LENGTH_SHORT).show()
        } else {

            val notesTitle = binding.notesTitle.text.toString()
            val notesDesc = binding.notesDesc.getMD()
            val colorhere = color

            val sdf = SimpleDateFormat("dd/M/yyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            val data =
                Notes(
                    null,
                    title = notesTitle,
                    notesdesc = notesDesc,
                    date = currentDate,
                    color = colorhere,
                    image = imagePath

                )

            viewModel.addNotes(data)

            Toast.makeText(
                activity?.applicationContext,
                "Note Created Successfully",
                Toast.LENGTH_SHORT
            ).show()

            Navigation.findNavController(it!!)
                .navigate(R.id.action_notesCreateFragment_to_homeFragment)

        }

    }


    companion object {

        @JvmStatic
        fun newInstance() =
            NotesCreateFragment().apply {
                arguments = Bundle().apply {

                }
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


        var intent = Intent()
        intent.type = "image/*"
        intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

//        var intent = Intent()
//        intent.setType("image/*")
//        intent.setAction(Intent.ACTION_GET_CONTENT)
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

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                val selectedImageUrl = data.data
                if (selectedImageUrl != null) {
                    try {

                        val inputStream =
                            requireActivity().contentResolver.openInputStream(selectedImageUrl)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        imagePage.setImageBitmap(bitmap)
                        imagePage.visibility = View.VISIBLE
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


}