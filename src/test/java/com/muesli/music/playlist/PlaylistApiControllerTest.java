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
    @DisplayName("?????????????????? ?????? ?????? TEST")
    public void retrievePlaylist() throws Exception {
        this.mockMvc.perform(get("/api/v1/playlist/{id}", 1L)
                        .param("size", "100")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("?????????????????? IDX")
                        ),
                        requestParameters(
                                parameterWithName("size").description("?????????????????? ?????? ?????? ??????"),
                                parameterWithName("page").description("?????????????????? ?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("?????????????????? IDX"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????????????????? ??????"),
                                fieldWithPath("data.isPublic").type(JsonFieldType.NUMBER).description("?????????????????? ????????????"),
                                fieldWithPath("data.image").type(JsonFieldType.STRING).description("?????????????????? ?????????").optional(),
                                fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("?????????????????? ?????????"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("?????????????????? ??????").optional(),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("?????????????????? ?????????"),
                                fieldWithPath("data.trackCount").type(JsonFieldType.NUMBER).description("?????????????????? ?????? ??????"),
                                fieldWithPath("data.userInfo.id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.userInfo.username").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.trackInfoList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.trackInfoList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.trackInfoList[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.trackInfoList[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.trackInfoList[].adult").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.trackInfoList[].isTitle").type(JsonFieldType.NUMBER).description("????????? ??? ??????").optional(),
                                fieldWithPath("data.trackInfoList[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.trackInfoList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.trackInfoList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.trackInfoList[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.trackInfoList[].albumName").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????????????????? ?????? TEST")
    public void retrieveMyPlaylist() throws Exception {
        this.mockMvc.perform(get("/api/v1/playlist/my")
                        .header("Authorization", JWT_TOKEN)
                        .param("size", "100")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("?????????????????? ?????? ?????? ??????"),
                                parameterWithName("page").description("?????????????????? ?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("?????????????????? IDX"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("?????????????????? ??????"),
                                fieldWithPath("data[].isPublic").type(JsonFieldType.NUMBER).description("?????????????????? ????????????"),
                                fieldWithPath("data[].image").type(JsonFieldType.STRING).description("?????????????????? ?????????").optional(),
                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("?????????????????? ?????????"),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("?????????????????? ??????").optional(),
                                fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("?????????????????? ?????????"),
                                fieldWithPath("data[].trackCount").type(JsonFieldType.NUMBER).description("?????????????????? ?????? ??????"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("???????????? ?????????????????? ?????? ?????? TEST")
    public void retrieveLikePlaylistList() throws Exception {
        this.mockMvc.perform(get("/api/v1/playlist/likeables")
                        .header("Authorization", JWT_TOKEN)
                        .param("size", "100")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("playlist/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("?????????????????? ?????? ?????? ??????"),
                                parameterWithName("page").description("?????????????????? ?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("?????????????????? IDX"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("?????????????????? ??????"),
                                fieldWithPath("data[].isPublic").type(JsonFieldType.NUMBER).description("?????????????????? ????????????"),
                                fieldWithPath("data[].image").type(JsonFieldType.STRING).description("?????????????????? ?????????").optional(),
                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("?????????????????? ?????????"),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("?????????????????? ??????").optional(),
                                fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("?????????????????? ?????????"),
                                fieldWithPath("data[].trackCount").type(JsonFieldType.NUMBER).description("?????????????????? ?????? ??????"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data[].userName").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("????????? ????????? ?????? TEST")
    public void registerPlaylist() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var requestParams = PlaylistDto.RegisterPlaylist.builder()
                .name("?????????????????? ??????1")
                .description("?????????????????? ??????1")
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
                                fieldWithPath("name").description("?????????????????? ??????"),
                                fieldWithPath("description").description("?????????????????? ??????"),
                                fieldWithPath("isPublic").description("?????????????????? ?????? ??????"),
                                fieldWithPath("userId").description("?????? IDX").ignored()

                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("?????????????????? IDX"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????????????????? ??????"),
                                fieldWithPath("data.isPublic").type(JsonFieldType.NUMBER).description("?????????????????? ????????????"),
                                fieldWithPath("data.image").type(JsonFieldType.STRING).description("?????????????????? ?????????").optional(),
                                fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("?????????????????? ?????????"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("?????????????????? ??????").optional(),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("?????????????????? ?????????"),
                                fieldWithPath("data.trackCount").type(JsonFieldType.NUMBER).description("?????????????????? ?????? ??????"),
                                fieldWithPath("data.userInfo.id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.userInfo.username").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.trackInfoList").type(JsonFieldType.ARRAY).description("?????? ?????????").optional()
                        )
                ));
    }

    @Test
    @DisplayName("????????? ????????? ?????? TEST")
    public void updatePlaylist() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Long> trackList = new ArrayList<>();
        trackList.add(1L);
        trackList.add(2L);
        trackList.add(3L);
        var requestParams = PlaylistDto.UpdatePlaylist.builder()
                .name("?????????????????? ??????2")
                .description("?????????????????? ??????2")
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
                                parameterWithName("id").description("?????????????????? IDX")
                        ),
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("name").description("?????????????????? ??????"),
                                fieldWithPath("description").description("?????????????????? ??????"),
                                fieldWithPath("isPublic").description("?????????????????? ?????? ??????"),
                                fieldWithPath("userId").description("?????? IDX").ignored(),
                                fieldWithPath("trackList").description("?????????????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("??????")
                        )
                ));
    }

//    @Test
//    @DisplayName("????????? ????????? ?????? TEST")
//    public void removePlaylist() throws Exception {
//        this.mockMvc.perform(delete("/api/v1/playlist/{id}", 2L)
//                        .header("Authorization", JWT_TOKEN)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(document("playlist/{method-name}",
//                        pathParameters(
//                                parameterWithName("id").description("?????????????????? IDX")
//                        ),
//                        responseFields(
//                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
//                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
//                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
//                                fieldWithPath("data").type(JsonFieldType.STRING).description("??????")
//                        )
//                ));
//    }

}
