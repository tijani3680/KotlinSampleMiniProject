package com.tijani.rememberkotlinskills.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tijani.rememberkotlinskills.core.model.FakeUsersItem
import com.tijani.rememberkotlinskills.core.repository.FakeUserRepository
import com.tijani.rememberkotlinskills.utils.NetworkHelper
import com.tijani.rememberkotlinskills.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class FakeUserVM(private val fakeUserRepository: FakeUserRepository,private val networkHelper: NetworkHelper ):ViewModel() {

    private var fakeUserLiveData: LiveData<Resource<List<FakeUsersItem>>>? = null

    fun getFakeUsersUsingFlow(): LiveData<Resource<List<FakeUsersItem>>>? {
        fakeUserLiveData = liveData(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                fakeUserRepository.getFakeUserFromServer()
                    .onStart { emit(Resource.loading(null)) }
                    .catch { exception -> emit(Resource.error(exception.cause.toString(), null)) }
                    .collect {
                        if (it.isSuccessful) {
                            emit(Resource.success(it.body()))
                            it.body()?.let { it1 -> fakeUserRepository.insertFakeUsersIntoLocalDb(it1) }


                        }
                    }
            }else {
                    fakeUserRepository.getFakeUserFromLocalDb()
                        .onStart { emit(Resource.loading(null)) }
                        .catch { exeption -> emit(Resource.error(exeption.message.toString(), null)) }
                        .collect { emit(Resource.success(it)) }
                }

        }
        return fakeUserLiveData
    }


}