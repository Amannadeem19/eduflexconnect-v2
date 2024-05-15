package com.fyp.eduflexconnect.Services;

import lombok.Data;

import java.util.List;

@Data
public class GroupChatRequest {

    private List<String> studentIds;
    private String group_name, group_image;
}
