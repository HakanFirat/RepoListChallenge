package com.example.repolistchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.repolistchallenge.network.RepoListApi
import com.example.repolistchallenge.core.BaseViewModel
import com.example.repolistchallenge.model.RepoModel
import com.example.repolistchallenge.network.ApiCallBack
import com.example.repolistchallenge.network.ApiClient
import com.example.repolistchallenge.network.ApiError

class RepoListViewModel: BaseViewModel() {

    var mutableRepoList: MutableLiveData<List<RepoModel>> = MutableLiveData()
    private var service: RepoListApi =
        ApiClient.instance.createService(RepoListApi::class.java)

    fun getRepoList(user: String,count: Int = 0) {
        setLoadingState()

        service.listRepos(user).enqueue(object : ApiCallBack<List<RepoModel>>() {
            override fun onSuccess(data: List<RepoModel>?) {
                if (data == null)
                    setErrorState()
                else {
                    setSuccessState(data)
                    mutableRepoList.value = data
                }
            }

            override fun onError(error: ApiError) {
                setErrorState(error)
            }
        })
    }
}