package my.app;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.databind.module.SimpleModule;

import com.fasterxml.jackson.core.JsonProcessingException;

import my.entity.GetListFilter;
import my.entity.SampleObject;
import my.entity.SampleObjectSerializer;
import ratpack.core.handling.Context;

public class Handler {
    public static void GetListOfObjects(Context context) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Map<String, String> params = context.getRequest().getQueryParams();
        String pageStr = params.get("page");
        String limitStr = params.get("limit");
        String search = params.get("search");
        String sortBy = params.get("sort_by");
        String sortType = params.get("sort_type");
        int page = 0;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException ex){}
        int limit = 0;
        try {
            limit = Integer.parseInt(limitStr);
        } catch (NumberFormatException ex){}
        GetListFilter filter = new GetListFilter(page, limit, search, sortBy, sortType);
        List<SampleObject> result = Database.GetListOfObjects(filter);
        if (result == null || result.isEmpty()) {
            context.render("No data found");
        } else {
            try {
                // Convert the object to JSON string
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                SimpleModule module = new SimpleModule();
                module.addSerializer(SampleObject.class, new SampleObjectSerializer());
                objectMapper.registerModule(module);
                String jsonData = objectMapper.writeValueAsString(result);
    
                // Return JSON data with HTTP status 200
                context.render(jsonData);
            } catch (JsonProcessingException e) {
                // Return an error response if conversion fails
                context.render("Error converting to JSON");
            }
        }
    }
      
    public static void GetObjectByID(Context context) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String idStr = context.getPathTokens().get("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex){}

        SampleObject result = Database.GetObjectByID(id);
        try {
            // Convert the object to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            SimpleModule module = new SimpleModule();
            module.addSerializer(SampleObject.class, new SampleObjectSerializer());
            objectMapper.registerModule(module);
            String jsonData = objectMapper.writeValueAsString(result);

            // Return JSON data with HTTP status 200
            context.render(jsonData);
        } catch (JsonProcessingException e) {
            // Return an error response if conversion fails
            context.render("Error converting to JSON");
        }
    }

    public static void InsertObject(Context context) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Map<String, String> params = context.getRequest().getQueryParams();
        String title = params.get("title");
        String description = params.get("description");
        String status = params.get("status");

        SampleObject data = new SampleObject(0, title, description, status, "", "");
        boolean res = Database.InsertObject(data);
        context.render("" + res);
    }

    public static void UpdateObject(Context context) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        Map<String, String> params = context.getRequest().getQueryParams();
        String idStr = context.getPathTokens().get("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex){}
        String title = params.get("title");
        String description = params.get("description");
        String status = params.get("status");

        SampleObject data = new SampleObject(id, title, description, status, "", "");
        boolean res = Database.UpdateObject(data);
        context.render("" + res);
    }

    public static void DeleteObject(Context context) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String idStr = context.getPathTokens().get("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex){}

        boolean res = Database.DeleteObject(id);
        context.render("" + res);
    }
}
