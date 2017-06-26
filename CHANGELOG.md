#CHANGE LOG

###v0.3
* New package name
* New `PermissionManager.askForPermission` method signature to make lambas easier.
* New `PermissionManager.initialize` method to make it clear that it depends on a `Context`
* New `PermissionManager.unregister` method to explicitly unregister `PermissionListener`s
* Properly handles multiple requests from the same permission group, without duplicate rationale dialogs
* Fix bug where app would crash if user rotated device while viewing system permission dialog

###v0.2
* Bug fix with "no resource found with ID 0x000"
* `PermissionRequestDelegateActivity` notifies `PermissionManager` inside its `onDestroy()` method. This way if a `PermissionListener` lives inside an Activity, said Activity is not in paused state when `PermissionListener.onResult()` is called.
* bump to latest dependencies and build tools

###v0.1
	
* initial release
