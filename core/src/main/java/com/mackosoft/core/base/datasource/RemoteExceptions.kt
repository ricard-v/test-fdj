package com.mackosoft.core.base.network

class NullResponseBody(apiCallName: String) :
    Exception("Got null body in response to <$apiCallName>")

class UnsuccessfulApiCall(apiCallName: String, errorCode: String) :
    Exception("<$apiCallName> failed with error code <$errorCode>")