#CHANGE LOG

###v0.2
* Bug fix with "no resource found with ID 0x000"
* `PermissionRequestDelegateActivity` notifies `PermissionManager` inside its `onDestroy()` method. This way if a `PermissionListener` lives inside an Activity, said Activity is not in paused state when `PermissionListener.onResult()` is called.
* bump to latest dependencies and build tools

###v0.1
	
* initial release
