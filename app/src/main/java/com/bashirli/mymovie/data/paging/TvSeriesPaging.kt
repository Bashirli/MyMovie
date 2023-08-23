package com.bashirli.mymovie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bashirli.mymovie.common.serviceEnum.TvSeriesServiceEnumType
import com.bashirli.mymovie.data.dto.tvseries.TvSeriesResult
import com.bashirli.mymovie.data.service.remote.Service

class TvSeriesPaging (
    private val service: Service,
    private val enumType:TvSeriesServiceEnumType,
    private val seriesId:Int=0
) : PagingSource<Int,TvSeriesResult>() {



    override fun getRefreshKey(state: PagingState<Int, TvSeriesResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvSeriesResult> {
       return try {

           val nextPage = params.key ?: 1

            val response = when(enumType){
                TvSeriesServiceEnumType.AIRING_TODAY->{
                    service.getTvSeriesAiringToday(nextPage)
                }
                TvSeriesServiceEnumType.DISCOVER->{
                    service.getDiscoverTvSeries(nextPage)
                }
                TvSeriesServiceEnumType.ON_THE_AIR->{
                    service.getTvSeriesOnTheAir(nextPage)
                }
                TvSeriesServiceEnumType.TRENDING->{
                    service.getTvSeriesTrending(page = nextPage)
                }
                TvSeriesServiceEnumType.TOP_RATED->{
                    service.getTvSeriesTopRated(nextPage)
                }
                TvSeriesServiceEnumType.POPULAR->{
                    service.getTvSeriesPopular(nextPage)
                }
                TvSeriesServiceEnumType.RECOMMENDATIONS->{
                    service.getTvSeriesRecommendations(seriesId)
                }
            }

           val data = response.body()?.tvSeriesResults.orEmpty()
           val prevKey = if(nextPage==1) null else nextPage.minus(1)
           val nextKey = if(nextPage==response.body()?.totalPages) null else nextPage.plus(1)


           LoadResult.Page(
               data = data,
               prevKey=prevKey,
               nextKey=nextKey
           )

       }catch (e:Exception){
           LoadResult.Error(e)
       }
    }
}



