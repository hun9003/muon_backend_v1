package com.muesli.music.playlist;

import com.muesli.music.JwtToken;
import com.muesli.music.MuonBackendV1Application;
import com.muesli.music.interfaces.playlist.PlaylistDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class PlaylistApiControllerTest {
    private MockMvc mockMvc;
    private final String JWT_TOKEN = JwtToken.JWT_TOKEN;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("playlist/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("플레이리스트 정보 조회 TEST")
    public void retrievePlaylist() throws Exception {
        this.mockMvc.perform(get("/api/v1/playlist/{id}", 1L)
                        .param("size", "100")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("플레이리스트 IDX")
                        ),
                        requestParameters(
                                parameterWithName("size").description("플레이리스트 트랙 표시 개수"),
                                parameterWithName("page").description("플레이리스트 트랙 페이지")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("플레이리스트 IDX"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("플레이리스트 이름"),
                                fieldWithPath("data.isPublic").type(JsonFieldType.NUMBER).description("플레이리스트 공개여부"),
                                fieldWithPath("data.image").type(JsonFieldType.STRING).description("플레이리스트 이미지").optional(),
                                fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("플레이리스트 조회수"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("플레이리스트 설명").optional(),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("플레이리스트 생성일"),
                                fieldWithPath("data.trackCount").type(JsonFieldType.NUMBER).description("플레이리스트 트랙 개수"),
                                fieldWithPath("data.userInfo.id").type(JsonFieldType.NUMBER).description("유저 IDX"),
                                fieldWithPath("data.userInfo.username").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.trackInfoList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.trackInfoList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.trackInfoList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.trackInfoList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.trackInfoList[].adult").type(JsonFieldType.NUMBER).description("성인 여부").optional(),
                                fieldWithPath("data.trackInfoList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀 곡 여부").optional(),
                                fieldWithPath("data.trackInfoList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로").optional(),
                                fieldWithPath("data.trackInfoList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.trackInfoList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.trackInfoList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.trackInfoList[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("나의 플레이리스트 목록 TEST")
    public void retrieveMyPlaylist() throws Exception {
        this.mockMvc.perform(get("/api/v1/playlist/my")
                        .header("Authorization", JWT_TOKEN)
                        .param("size", "100")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("플레이리스트 트랙 표시 개수"),
                                parameterWithName("page").description("플레이리스트 트랙 페이지")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("플레이리스트 IDX"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("플레이리스트 이름"),
                                fieldWithPath("data[].isPublic").type(JsonFieldType.NUMBER).description("플레이리스트 공개여부"),
                                fieldWithPath("data[].image").type(JsonFieldType.STRING).description("플레이리스트 이미지").optional(),
                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("플레이리스트 조회수"),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("플레이리스트 설명").optional(),
                                fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("플레이리스트 생성일"),
                                fieldWithPath("data[].trackCount").type(JsonFieldType.NUMBER).description("플레이리스트 트랙 개수"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("유저 IDX"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("유저 이름")
                        )
                ));
    }

    @Test
    @DisplayName("좋아하는 플레이리스트 목록 조회 TEST")
    public void retrieveLikePlaylistList() throws Exception {
        this.mockMvc.perform(get("/api/v1/playlist/likeables")
                        .header("Authorization", JWT_TOKEN)
                        .param("size", "100")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("플레이리스트 트랙 표시 개수"),
                                parameterWithName("page").description("플레이리스트 트랙 페이지")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("플레이리스트 IDX"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("플레이리스트 이름"),
                                fieldWithPath("data[].isPublic").type(JsonFieldType.NUMBER).description("플레이리스트 공개여부"),
                                fieldWithPath("data[].image").type(JsonFieldType.STRING).description("플레이리스트 이미지").optional(),
                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("플레이리스트 조회수"),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("플레이리스트 설명").optional(),
                                fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("플레이리스트 생성일"),
                                fieldWithPath("data[].trackCount").type(JsonFieldType.NUMBER).description("플레이리스트 트랙 개수"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("유저 IDX"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("유저 이름")
                        )
                ));
    }

    @Test
    @DisplayName("플레이 리스트 생성 TEST")
    public void registerPlaylist() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var requestParams = PlaylistDto.RegisterPlaylist.builder()
                .name("플레이리스트 제목1")
                .description("플레이리스트 설명1")
                .isPublic(1L)
                .build();
        this.mockMvc.perform(post("/api/v1/playlist")
                        .header("Authorization", JWT_TOKEN)
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(requestParams))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        requestFields(
                                fieldWithPath("name").description("플레이리스트 제목"),
                                fieldWithPath("description").description("플레이리스트 설명"),
                                fieldWithPath("isPublic").description("플레이리스트 공개 여부"),
                                fieldWithPath("userId").description("유저 IDX").ignored()

                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("플레이리스트 IDX"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("플레이리스트 이름"),
                                fieldWithPath("data.isPublic").type(JsonFieldType.NUMBER).description("플레이리스트 공개여부"),
                                fieldWithPath("data.image").type(JsonFieldType.STRING).description("플레이리스트 이미지").optional(),
                                fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("플레이리스트 조회수"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("플레이리스트 설명").optional(),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("플레이리스트 생성일"),
                                fieldWithPath("data.trackCount").type(JsonFieldType.NUMBER).description("플레이리스트 트랙 개수"),
                                fieldWithPath("data.userInfo.id").type(JsonFieldType.NUMBER).description("유저 IDX"),
                                fieldWithPath("data.userInfo.username").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.trackInfoList").type(JsonFieldType.ARRAY).description("트랙 리스트").optional()
                        )
                ));
    }

    @Test
    @DisplayName("플레이 리스트 수정 TEST")
    public void updatePlaylist() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Long> trackList = new ArrayList<>();
        trackList.add(1L);
        trackList.add(2L);
        trackList.add(3L);
        var requestParams = PlaylistDto.UpdatePlaylist.builder()
                .name("플레이리스트 제목2")
                .description("플레이리스트 설명2")
                .isPublic(1L)
                .trackList(trackList)
                .build();
        this.mockMvc.perform(put("/api/v1/playlist/{id}", 1L)
                        .header("Authorization", JWT_TOKEN)
                        .header("content-type", "application/json")
                        .content(mapper.writeValueAsString(requestParams))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("플레이리스트 IDX")
                        ),
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("name").description("플레이리스트 제목"),
                                fieldWithPath("description").description("플레이리스트 설명"),
                                fieldWithPath("isPublic").description("플레이리스트 공개 여부"),
                                fieldWithPath("userId").description("유저 IDX").ignored(),
                                fieldWithPath("trackList").description("플레이리스트 트랙")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("상태")
                        )
                ));
    }

    @Test
    @DisplayName("플레이 리스트 삭제 TEST")
    public void removePlaylist() throws Exception {
        this.mockMvc.perform(delete("/api/v1/playlist/{id}", 3L)
                        .header("Authorization", JWT_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("플레이리스트 IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("상태")
                        )
                ));
    }

}
