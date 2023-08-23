package com.bashirli.mymovie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bashirli.mymovie.data.dto.celebs.CelebsResult
import com.bashirli.mymovie.data.service.remote.Service

class CelebsPaging constructor(
    private val service: Service
) : PagingSource<Int,CelebsResult>() {

    override fun getRefreshKey(state: PagingState<Int, CelebsResult>): Int? {
       return state.anchorPosition?.let {
           val anchorPage = state.closestPageToPosition(it)
           anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
       }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CelebsResult> {
      return try {
          val nextPageNumber = params.key ?: 1
          val response=service.getCelebs(nextPageNumber)
          LoadResult.Page(
              data = response.body()?.celebsResults.orEmpty(),
              prevKey = if(nextPageNumber==1) null else nextPageNumber.minus(1),
              nextKey = if(nextPageNumber==response.body()?.totalPages) null else nextPageNumber.plus(1)
          )

       }catch (e:Exception){
           LoadResult.Error(throwable = e)
       }
    }


}