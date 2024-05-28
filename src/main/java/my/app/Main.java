package my.app;

import ratpack.core.server.RatpackServer;

public class Main {
 public static void main(String... args) throws Exception {
   RatpackServer.start(server -> server 
     .handlers(chain -> chain
       .post("insert", ctx -> Handler.InsertObject(ctx))   
       .put("update/:id", ctx -> Handler.UpdateObject(ctx))
       .put("delete/:id", ctx -> Handler.DeleteObject(ctx))
       .get(ctx -> Handler.GetListOfObjects(ctx))
       .get(":id", ctx -> Handler.GetObjectByID(ctx))
     )
   );
 }
}