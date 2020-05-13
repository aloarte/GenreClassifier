package com.p4r4d0x.edmgenreclassifier.datasource.repository

class ServiceResponse<T>(var status: Int, var response: T?, var serviceError: Error?)
