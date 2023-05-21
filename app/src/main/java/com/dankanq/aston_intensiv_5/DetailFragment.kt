package com.dankanq.aston_intensiv_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

class DetailFragment : Fragment() {
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var phoneNumber: String

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextPhoneNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            editTextFirstName = findViewById(R.id.et_first_name)
            editTextLastName = findViewById(R.id.et_last_name)
            editTextPhoneNumber = findViewById(R.id.et_phone_number)

            findViewById<Button>(R.id.b_save).apply {
                setOnClickListener {
                    saveContact()
                }
            }
        }

        setContactData()
    }

    private fun setContactData() {
        editTextFirstName.setText(firstName)
        editTextLastName.setText(lastName)
        editTextPhoneNumber.setText(phoneNumber)
    }

    private fun saveContact() {
        parentFragmentManager.popBackStack()
        val bundle = Bundle().apply {
            putString(BUNDLE_FIRST_NAME_KEY, editTextFirstName.text.toString())
            putString(BUNDLE_LAST_NAME_KEY, editTextLastName.text.toString())
            putString(BUNDLE_PHONE_NUMBER_KEY, editTextPhoneNumber.text.toString())
        }
        setFragmentResult(RESULT_KEY, bundle)
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (args.containsKey(FIRST_NAME_KEY) && args.containsKey(LAST_NAME_KEY) &&
            args.containsKey(PHONE_NUMBER_KEY)
        ) {
            firstName = args.getString(FIRST_NAME_KEY) ?: UNDEFINED_STRING_VALUE
            lastName = args.getString(LAST_NAME_KEY) ?: UNDEFINED_STRING_VALUE
            phoneNumber = args.getString(PHONE_NUMBER_KEY) ?: UNDEFINED_STRING_VALUE
        }
    }

    companion object {
        private const val FIRST_NAME_KEY = "first_name"
        private const val LAST_NAME_KEY = "last_name"
        private const val PHONE_NUMBER_KEY = "phone_number"

        private const val UNDEFINED_STRING_VALUE = ""

        const val RESULT_KEY = "result"
        const val BUNDLE_FIRST_NAME_KEY = "bundle_first_name"
        const val BUNDLE_LAST_NAME_KEY = "bundle_last_name"
        const val BUNDLE_PHONE_NUMBER_KEY = "bundle_phone_number"

        fun newInstance(firstName: String, lastName: String, phoneNumber: String): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(FIRST_NAME_KEY, firstName)
                    putString(LAST_NAME_KEY, lastName)
                    putString(PHONE_NUMBER_KEY, phoneNumber)
                }
            }
        }
    }
}