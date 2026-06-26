package com.task.dto;

import java.util.ArrayList;
import java.util.List;

import com.task.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
	private String title;
	private String description;
	private String image;
	private Long assignedUserId;
	private List<String> tags = new ArrayList<>();
	private TaskStatus status;
}
