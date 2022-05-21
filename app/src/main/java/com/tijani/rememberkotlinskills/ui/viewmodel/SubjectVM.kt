package com.tijani.rememberkotlinskills.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.tijani.rememberkotlinskills.core.model.SubjectM
import com.tijani.rememberkotlinskills.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class SubjectVM : ViewModel() {
    private var subjectLiveData: LiveData<Resource<List<SubjectM>>>? = null

    fun getSubject(): LiveData<Resource<List<SubjectM>>>? {
        subjectLiveData = liveData(Dispatchers.IO) {
            generateSubjects()
                .catch { exception -> emit(Resource.error(exception.cause.toString(), null)) }
                .collect {
                    emit(Resource.success(it))
                }


        }
        return subjectLiveData

    }


    private suspend fun generateSubjects(): Flow<ArrayList<SubjectM>> {
        return flow {
            var subjects = arrayListOf<SubjectM>()
            subjects.add(SubjectM(1, "Data Store"))
            subjects.add(SubjectM(2, "Kotlin Flow"))
            subjects.add(SubjectM(3, "Kotlin Class Delegation"))
            subjects.add(SubjectM(4, "Kotlin Properties Delegation"))
            subjects.add(SubjectM(5, "Kotlin Observable Delegation"))
            emit(subjects)
        }.flowOn(Dispatchers.IO)
    }


}