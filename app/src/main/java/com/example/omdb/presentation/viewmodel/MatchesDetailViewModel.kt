package com.example.omdb.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.starwars.starwarstournament.common.dto.matchdetails.MatchItem
import com.starwars.starwarstournament.domain.GetMatchesUseCase
import javax.inject.Inject

class MatchesDetailViewModel @Inject constructor(
    private val matchesDetailUseCase: GetMatchesUseCase
) : BaseViewModel() {

    val matchesDetailData: LiveData<List<MatchItem>>
        get() = _matchesDetailData

    private val _matchesDetailData = MutableLiveData<List<MatchItem>>()

    fun getMatchesDetailsData(playerId: Int) {
        startBackgroundJob({
            matchesDetailUseCase.getMatchesList(playerId)
        }, {
            _matchesDetailData.value = it
        })
    }
}
