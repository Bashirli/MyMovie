package com.bashirli.mymovie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bashirli.mymovie.common.serviceEnum.ServiceEnumType
import com.bashirli.mymovie.data.dto.movies.MoviesDTO
import com.bashirli.mymovie.data.dto.movies.Result
import com.bashirli.mymovie.data.mapper.toResultModel
import com.bashirli.mymovie.data.service.Service
import com.bashirli.mymovie.domain.model.movies.MoviesModel
import javax.inject.Inject

class MoviesPaging  constructor(
    val service: Service,
    val serviceEnumType: ServiceEnumType,
    val movie_id:Int=1,
    val query:String=""
) : PagingSource<Int,Result>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
       return  try {
            val nextPageNumber=params.key?:1
            val response = when(serviceEnumType){
                ServiceEnumType.GET_MOVIES->{
                    service.getMovies(nextPageNumber)
                }
                ServiceEnumType.DAILY_TRENDING_MOVIES->{
                    service.getTrendingDayMovies(nextPageNumber)
                }
                ServiceEnumType.UPCOMING_MOVIES->{
                    service.getUpcomingMovies(nextPageNumber)
                }
                ServiceEnumType.FOR_YOU->{
                    service.getForYouMovies(nextPageNumber)
                }
                ServiceEnumType.RECOMMENDATIONS->{
                    service.getRecommendations(movie_id,nextPageNumber)
                }
                ServiceEnumType.SEARCH ->{
                    service.getSearchMovie(query,nextPageNumber)
                }
        }
               LoadResult.Page(
                data=response.body()?.results.orEmpty(),
                prevKey = if(nextPageNumber==1) null else nextPageNumber.minus(1),
                nextKey = if(nextPageNumber==response.body()?.totalPages) null else nextPageNumber.plus(1)
               )

        }catch (e:Exception){
              LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
                anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}