package com.tijani.rememberkotlinskills.utils

import com.tijani.rememberkotlinskills.utils.mInterface.Logger

/**
 * this is class delegation
 */
class Controller(logger: Logger) :Logger by logger{
}