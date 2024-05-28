package my.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class SampleObjectSerializer extends JsonSerializer<SampleObject> {
    @Override
    public void serialize(SampleObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject(); // Start JSON object

        // Write properties to JSON object
        gen.writeNumberField("id", value.GetID());
        gen.writeStringField("title", value.GetTitle());
        gen.writeStringField("description", value.GetDescription());
        gen.writeStringField("status", value.GetStatus());
        gen.writeStringField("create_time", value.GetCreateDate());
        gen.writeStringField("update_time", value.GetUpdateDate());
        // Add other properties as needed
        
        gen.writeEndObject(); // End JSON object
    }
}