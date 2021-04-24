package com.l2hyunwoo.android.presentation.main.subscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.l2hyunwoo.android.R
import com.l2hyunwoo.android.databinding.ItemMainRepoBinding
import com.l2hyunwoo.android.databinding.ItemMainRepoHeaderBinding
import com.l2hyunwoo.android.domain.entity.GithubRepository

sealed class UIModel {
    object Header : UIModel()
    class Repository(val githubRepository: GithubRepository) : UIModel()
}

abstract class RepositoryViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun onBind(uiModel: UIModel)
}

class RepositoryItemViewHolder(private val binding: ItemMainRepoBinding) :
    RepositoryViewHolder(binding) {
    override fun onBind(uiModel: UIModel) {
        binding.repository = (uiModel as UIModel.Repository).githubRepository
    }
}

class RepositoryHeaderViewHolder(private val binding: ItemMainRepoHeaderBinding) :
    RepositoryViewHolder(binding) {
    override fun onBind(uiModel: UIModel) {}
}

class RepositoryListAdapter : RecyclerView.Adapter<RepositoryViewHolder>() {
    private val repositoryList = mutableListOf<UIModel>()

    override fun getItemViewType(position: Int): Int {
        return when (repositoryList[position]) {
            is UIModel.Header -> HEADER
            is UIModel.Repository -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            HEADER -> DataBindingUtil.inflate<ItemMainRepoHeaderBinding>(
                layoutInflater,
                R.layout.item_main_repo_header,
                parent,
                false
            )
            ITEM -> DataBindingUtil.inflate<ItemMainRepoBinding>(
                layoutInflater,
                R.layout.item_main_repo,
                parent,
                false
            )
            else -> throw IllegalArgumentException("Error View Type: $viewType")
        }
        return when(binding) {
            is ItemMainRepoHeaderBinding -> RepositoryHeaderViewHolder(binding)
            is ItemMainRepoBinding -> RepositoryItemViewHolder(binding)
            else -> throw IllegalArgumentException("Error Binding Type: ${binding.javaClass}")
        }
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(repositoryList[position])
    }

    override fun getItemCount(): Int = repositoryList.size

    fun submitList(newList: MutableList<UIModel>) {
        repositoryList.clear()
        repositoryList.addAll(newList)
        notifyDataSetChanged()
    }

    companion object {
        const val HEADER = 0
        const val ITEM = 1
    }
}