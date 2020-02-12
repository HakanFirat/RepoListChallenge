package com.example.repolistchallenge.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.repolistchallenge.R
import com.example.repolistchallenge.core.BaseFragment
import com.example.repolistchallenge.extensions.hideKeyboard
import com.example.repolistchallenge.model.RepoModel
import com.example.repolistchallenge.network.State
import com.example.repolistchallenge.utils.DialogUtils
import com.example.repolistchallenge.viewmodel.RepoListViewModel
import kotlinx.android.synthetic.main.fragment_repo_list.*


class RepoListFragment : BaseFragment(), View.OnClickListener {

    private lateinit var viewModel: RepoListViewModel
    private var repoList: List<RepoModel> = emptyList()
    private var repoListAdapter: RepoListAdapter? = null
    private var user_input: String? = null

    override val layoutResource: Int
        get() = R.layout.fragment_repo_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RepoListViewModel::class.java)
        observeViewModel()
    }

    override fun initViewsOnViewCreated(view: View) {
        super.initViewsOnViewCreated(view)

        hideBackButton()
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        submitButton.setOnClickListener(this)
        if (!user_input.isNullOrEmpty()){
            viewModel.getRepoList(user_input!!)
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.submitButton -> {
                v.hideKeyboard()
                userName.text?.let {
                    user_input = userName.text.toString()
                    viewModel.getRepoList(it.toString())
                }
            }
        }
    }

    private fun observeViewModel() {
        val observer = Observer<State> { value ->
            value?.let {
                when (it) {
                    is State.Loading -> {
                        Toast.makeText(context, "Bekleyiniz...", Toast.LENGTH_SHORT).show()
                    }

                    is State.Success<*> -> {
                        Toast.makeText(context, "Sonuç geldi.", Toast.LENGTH_SHORT).show()
                        renderModel(it.data!!)
                    }

                    is State.Error -> {
                        Toast.makeText(context, "Hata oldu.", Toast.LENGTH_SHORT).show()
                        DialogUtils.handleApiError(context, it.error, true)
                    }
                }
            }
        }
        viewModel.state.observe(this, observer)
    }

    private fun renderModel(model: Any) {
        if (model is List<*>) {
            repoList = emptyList()
            repoList = model as List<RepoModel>

            if (!repoList.isNullOrEmpty()){

                repoListAdapter = RepoListAdapter(repoList)
                repoListAdapter?.setItemClickListener(object : RepoListAdapter.ItemClickListener{
                    override fun onItemClick(view: View, position: Int) {
                        navigateTo(R.id.action_fragmentRepoList_to_fragmentRepoDetail, Bundle().apply {
                            putSerializable("ITEM", repoList[position])
                        })

                    }
                })
                recyclerView.adapter = repoListAdapter
            }
        }
    }
}
