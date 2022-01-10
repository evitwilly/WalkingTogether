package ru.freeit.walkingtogether.presentation.screens.name

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.freeit.walkingtogether.R
import ru.freeit.walkingtogether.core.delegates.viewBinding
import ru.freeit.walkingtogether.core.extensions.error
import ru.freeit.walkingtogether.core.extensions.gone
import ru.freeit.walkingtogether.core.extensions.str
import ru.freeit.walkingtogether.core.extensions.visible
import ru.freeit.walkingtogether.databinding.NameScreenBinding
import ru.freeit.walkingtogether.presentation.base.BaseFragment
import ru.freeit.walkingtogether.presentation.screens.name.ui.NameEditState

class NameScreen : BaseFragment(R.layout.name_screen) {

    private val binding by viewBinding(NameScreenBinding::bind)
    private var viewModel : NameViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { navigator.back() }
        binding.toolbar.title = ""

        viewModel = ViewModelProvider(this, factories.name()).get(NameViewModel::class.java)

        viewModel?.fillTextFields(binding.nameEdit, binding.bioEdit)

        viewModel?.observe(viewLifecycleOwner) { state ->
            binding.progress.gone()
            when (state) {
                is NameEditState.Loading -> {
                    binding.progress.visible()
                }
                is NameEditState.Success -> {
                    navigator.back()
                }
                is NameEditState.NameEmpty -> {
                    binding.nameBox.error(R.string.name_is_emtpy)
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.apply_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.apply -> {
                viewModel?.apply(binding.nameEdit.str(), binding.bioEdit.str())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}