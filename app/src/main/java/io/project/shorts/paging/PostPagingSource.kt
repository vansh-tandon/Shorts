package io.project.shorts.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.project.shorts.api.PostAPI
import io.project.shorts.models.current.PostsItem
import io.project.shorts.models.current.VideoResponse

class PostPagingSource(val PostAPI: PostAPI): PagingSource<Int, PostsItem>() {
    override fun getRefreshKey(state: PagingState<Int, PostsItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostsItem> {
        TODO("Not yet implemented")
    }
}