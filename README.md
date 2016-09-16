# Async-Remote-Resource-Loader
Use this library to quickly fetch a remote resource, be it a Json 
object or an Image file or Maps. 

Add the dependency in your build.gradle file of your application. 
![boardActivityWithRecyclerView.png]({{site.baseurl}}/boardActivityWithRecyclerView.png)


## Classes to use

###  ResourceManager
  *Gives the functionality of a LruCache, and a generic function for loading a resource by providing the URL.* 
  
  
      
  **getInstance(Context mContext)** method provides an instance of the ResourceManager class for you to interact with.
  ```java       
            ResourceManager resourceManager = ResourceManager.getInstance(getApplicationContext());
   ```      
      
  **setMaxMemorySize(int maxSize)** can be used to set the maximum size of the available cache memory depending upon your application's need.
  By default it is set 1/8 of the total memory allocated to your application at runtime. 
  ```java       
           resourceManager.setMaxMemorySize(int maxSize) 
  ```      
      
  **getConvertersUtils()** can be used to use the ConverterUtils interface to convert the received byte array into a Bitmap object or an UTF-8 encoded string. 
  ```java      
            resourceManager.getConvertersUtils().byteArrayToBitmap(byte[] receivedBytes)
           
            resourceManager.getConvertersUtils().byteArrayToUri(byte[] receivedBytes)
  ```



###  ResourceLoader
  *The wrapper class for downloading any resource and have it's data served as a byte array.*
  
  
     
  The **getResourceLoader()** method returns an instance of the ResourceLoader class.
  ```java    
            resourceManager.getResourceLoader()
  ```
      
 The **fetchResource(String URL, OnDownloadCompletionListener yourListener, View onCancelView)** method lets you download the resource. 
 
 **_OnDownloadCompletionListener_** interface allows you to get notified when the resource has been loaded, so that you can perform appropriate actions.
 **_onCancelView_** allows you to pass a View object to the fetchResource method, which implicitly sets an OnClickListener for you to cancel the resource loading at any point.

```java      
      resourceManager.getResourceLoader().fetchResource
                    ("http://your-remote-resource-url", new OnDownloadCompletionListener() {
                        @Override
                        public void resourceFetchedFromRemote(String url, byte[] bytes) {
                            resourceLoaded(url, bytes);
                        }
    
                        @Override
                        public void resourceFetchedFromMemory(String url, byte[] bytes) {
                            resourceLoaded(url, bytes);
                        }
    
                        @Override
                        public void resourceDownloadCancelled(String url) {
                            resourceCancelled(url);
                        }
    
                    },
                    onCancelView);
  ```
