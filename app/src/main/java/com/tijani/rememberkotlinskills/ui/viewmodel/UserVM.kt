package com.tijani.rememberkotlinskills.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tijani.rememberkotlinskills.core.model.UserM
import com.tijani.rememberkotlinskills.core.repository.UserRepository
import com.tijani.rememberkotlinskills.utils.NetworkHelper
import com.tijani.rememberkotlinskills.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class UserVM(private val userRepository: UserRepository, private val networkHelper: NetworkHelper) :
    ViewModel() {

    private var userListLiveData: LiveData<Resource<List<UserM>>>? = null
    private val TAG = "MainActivityTag"


    fun getUsers(): LiveData<Resource<List<UserM>>>? {
        userListLiveData = liveData(Dispatchers.IO) {
            emit(Resource.loading(null))

            if (networkHelper.isNetworkConnected()) {
                try {
                    userRepository.getUserListFromServer().let {
                        if (it.isSuccessful) {
                            emit(Resource.success(it.body()))
                            it.body()?.let { it1 -> userRepository.addUsersIntoLocalDb(it1) }
                        } else {
                            emit(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } catch (e: Exception) {
                    // when server is down
                    emit(Resource.error("SERVER Is Down", null))
                    userRepository.getUserListFromLocalDb().let {
                        emit(Resource.success(it))
                    }

                }

            } else {
                // when internet is off
                userRepository.getUserListFromLocalDb().let {
                    emit(Resource.success(it))
                }
                emit(Resource.error("No internet connection", null))
            }
        }

        return userListLiveData
    }


    fun getUsersUsingFlow(): LiveData<Resource<List<UserM>>>? {
        userListLiveData = liveData(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                userRepository.getUserListFromServerFlow()
                    .onStart { emit(Resource.loading(null)) }
                    .catch { exception -> emit(Resource.error(exception.cause.toString(), null)) }
                    .collect {
                        if (it.isSuccessful) {
                            emit(Resource.success(it.body()))
                            it.body()?.let { it1 -> userRepository.addUsersIntoLocalDb(it1) }
                        }
                    }
            } else {
                userRepository.getUserListFromLocalDbFlow()
                    .onStart { emit(Resource.loading(null)) }
                    .catch { exeption -> emit(Resource.error(exeption.message.toString(), null)) }
                    .collect { emit(Resource.success(it)) }
            }
        }
        return userListLiveData
    }


}