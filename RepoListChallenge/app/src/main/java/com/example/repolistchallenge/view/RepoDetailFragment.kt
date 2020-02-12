package com.example.repolistchallenge.view

import android.os.Bundle
import android.view.View
import com.example.repolistchallenge.R
import com.example.repolistchallenge.core.BaseFragment
import com.example.repolistchallenge.model.RepoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoDetailFragment : BaseFragment() {

    private var repoModel: RepoModel? = null
    override val layoutResource: Int
        get() = R.layout.fragment_repo_detail


    override fun onArguments(arguments: Bundle) {
        repoModel = arguments.getSerializable("ITEM") as RepoModel
    }
    override fun initViewsOnViewCreated(view: View) {
        super.initViewsOnViewCreated(view)

        repoModel?.name?.let {
            toolbarTitle = it
        }

        showBackButton()
        Picasso.get().load(repoModel?.owner?.avatar_url).into(avatarImageView)
        ownerTextView.text = repoModel?.owner?.login
        starsTextView.text = String.format(context!!.getString(R.string.stars),repoModel?.stargazers_count)
        openIssueTextView.text = String.format(context!!.getString(R.string.open_issue),repoModel?.open_issues)


    }


}
