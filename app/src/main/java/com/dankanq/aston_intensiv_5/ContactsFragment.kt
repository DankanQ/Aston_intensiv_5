package com.dankanq.aston_intensiv_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.dankanq.aston_intensiv_5.DetailFragment.Companion.BUNDLE_FIRST_NAME_KEY
import com.dankanq.aston_intensiv_5.DetailFragment.Companion.BUNDLE_LAST_NAME_KEY
import com.dankanq.aston_intensiv_5.DetailFragment.Companion.BUNDLE_PHONE_NUMBER_KEY
import com.dankanq.aston_intensiv_5.DetailFragment.Companion.RESULT_KEY

class ContactsFragment : Fragment() {
    private lateinit var contactsLinearLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsLinearLayout = view.findViewById(R.id.contacts_linear_layout)

        setFragmentResultListener()

        loadContactsInLinearLayout()
    }

    private fun setFragmentResultListener() {
        setFragmentResultListener(RESULT_KEY) { _, bundle ->
            val firstName = bundle.getString(BUNDLE_FIRST_NAME_KEY).orEmpty()
            val lastName = bundle.getString(BUNDLE_LAST_NAME_KEY).orEmpty()
            val phoneNumber = bundle.getString(BUNDLE_PHONE_NUMBER_KEY).orEmpty()

            val position = launchedItemPosition
            val itemContactView = contactsLinearLayout.getChildAt(position)

            val tvFirstName = itemContactView.findViewById<TextView>(R.id.tv_first_name)
            val tvLastName = itemContactView.findViewById<TextView>(R.id.tv_last_name)
            val tvPhoneNumber = itemContactView.findViewById<TextView>(R.id.tv_phone_number)

            tvFirstName.text = firstName
            tvLastName.text = lastName
            tvPhoneNumber.text = phoneNumber

            contacts[launchedItemPosition].apply {
                this.firstName = firstName
                this.lastName = lastName
                this.phoneNumber = phoneNumber
            }
        }
    }

    private fun loadContactsInLinearLayout() {
        for (contact in contacts) {
            val itemContactView = View.inflate(requireContext(), R.layout.item_contact, null)

            val tvFirstName = itemContactView.findViewById<TextView>(R.id.tv_first_name)
            val tvLastName = itemContactView.findViewById<TextView>(R.id.tv_last_name)
            val tvPhoneNumber = itemContactView.findViewById<TextView>(R.id.tv_phone_number)

            tvFirstName.text = contact.firstName
            tvLastName.text = contact.lastName
            tvPhoneNumber.text = contact.phoneNumber

            itemContactView.setOnClickListener {
                launchDetailFragment(itemContactView, contact)
            }

            contactsLinearLayout.addView(itemContactView)
        }
    }

    private fun launchDetailFragment(view: View, contact: Contact) {
        launchedItemPosition = contactsLinearLayout.indexOfChild(view)

        val detailFragment = DetailFragment.newInstance(
            contact.firstName,
            contact.lastName,
            contact.phoneNumber
        )
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = ContactsFragment()

        private var launchedItemPosition = -1
        private val contacts = listOf(
            Contact(0, "first_name_1", "last_name_1", "79001111111"),
            Contact(1, "first_name_2", "last_name_2", "79002222222"),
            Contact(2, "first_name_3", "last_name_3", "79003333333"),
            Contact(3, "first_name_4", "last_name_4", "79004444444"),
        )
    }
}