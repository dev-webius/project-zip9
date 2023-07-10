package com.zip9.api.LH.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LHAnnouncementListResponse {
    @JsonIgnore
    @JsonProperty("resHeader")
    LHResponseHeader LHResponseHeader;
    @JsonProperty("dsList")
    List<LHAnnouncementResponse> lhAnnouncements;
}
