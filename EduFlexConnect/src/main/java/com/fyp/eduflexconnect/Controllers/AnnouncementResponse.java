package com.fyp.eduflexconnect.Controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnnouncementResponse<T>
{
    @JsonProperty("type")
    private String type;
    @JsonProperty("isUpdated")
    private boolean isUpdated;
    @JsonProperty("announcement")
    private T announcement;
}
