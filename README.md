# vertx-sessionmap
The class SessionMap is a utility class which allows session data to be stored in LocalMap with a built-in timeout.

Example:

```
LocalMap<String, SessionMap> localMap = vertx.sharedData().getLocalMap("myLocalMap");
final String sessionId = routingContext.session().id();
SessionMap sessionMap = localMap.get(sessionId);
if (sessionMap == null) {
  sessionMap = new SessionMap(localMap, sessionId, vertx);
  localMap.put(sessionId, sessionMap);
}
localMap.put("key","value");
``` 
