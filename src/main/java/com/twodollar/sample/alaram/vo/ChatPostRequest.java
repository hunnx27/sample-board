package com.twodollar.sample.alaram.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatPostRequest {
	@NotNull(message="전송메시지는 필수값입니다.")
	@ApiModelProperty(required = true, value="전송메시지", example = "Hello World")
	private String snd_msg;
	@NotNull(message="전송요청자는 필수값입니다.")
	@ApiModelProperty(required = true, value="전송요청자", example = "PMS")
	private String snd_req_id;
	@NotNull(message="채널ID는 필수값입니다.")
	@ApiModelProperty(required = true, value="채널ID는 필수값입니다.", example = "CH-0001")
	private String channel_id;
}
