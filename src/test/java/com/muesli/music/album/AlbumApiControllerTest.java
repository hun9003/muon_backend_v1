package com.muesli.music.album;

import com.muesli.music.JwtToken;
import com.muesli.music.MuonBackendV1Application;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
class AlbumApiControllerTest {
    private MockMvc mockMvc;
    private final String JWT_TOKEN = JwtToken.JWT_TOKEN;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("album/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("?????? ?????? TEST")
    public void retrieveAlbum() throws Exception {
        this.mockMvc.perform(get("/api/v1/album/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("album/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("?????? IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.releaseDate").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.originalName").type(JsonFieldType.STRING).description("?????? ??????(??????)").optional(),
                                fieldWithPath("data.image").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.artistInfo.id").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.artistInfo.name").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.artistInfo.originalName").type(JsonFieldType.STRING).description("???????????? ??????(??????)").optional(),
                                fieldWithPath("data.artistInfo.englishName").type(JsonFieldType.STRING).description("???????????? ??????(??????)").optional(),
                                fieldWithPath("data.trackList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.trackList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.trackList[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.trackList[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.trackList[].adult").type(JsonFieldType.NUMBER).description("19??? ??????").optional(),
                                fieldWithPath("data.trackList[].isTitle").type(JsonFieldType.NUMBER).description("???????????? ??????").optional(),
                                fieldWithPath("data.trackList[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.trackList[].number").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.trackList[].duration").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.trackList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.trackList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.trackList[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.trackList[].albumName").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ?????? TEST")
    public void retrieveNewAlbum() throws Exception {
        this.mockMvc.perform(get("/api/v1/album/new")
                        .param("size", "50")
                        .param("page", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("album/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("?????? ??????"),
                                parameterWithName("page").description("?????????")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].releaseDate").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.list[].originalName").type(JsonFieldType.STRING).description("?????? ??????(??????)").optional(),
                                fieldWithPath("data.list[].image").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.list[].trackList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].trackList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].trackList[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.list[].trackList[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].adult").type(JsonFieldType.NUMBER).description("19??? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("???????????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].trackList[].number").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].duration").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.list[].trackList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.list[].trackList[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].trackList[].albumName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.list[].trackList[].image").type(JsonFieldType.STRING).description("?????? ?????????").optional()
                        )
                ));
    }

    @Test
    @DisplayName("???????????? ?????? ?????? TEST")
    public void retrieveLikeAlbumList() throws Exception {
        this.mockMvc.perform(get("/api/v1/album/likeables")
                        .header("Authorization", JWT_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("album/{method-name}",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].releaseDate").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.list[].originalName").type(JsonFieldType.STRING).description("?????? ??????(??????)").optional(),
                                fieldWithPath("data.list[].image").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.list[].trackList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].trackList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].trackList[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.list[].trackList[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].adult").type(JsonFieldType.NUMBER).description("19??? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("???????????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].trackList[].number").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].duration").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.list[].trackList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.list[].trackList[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].trackList[].albumName").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.list[].trackList[].image").type(JsonFieldType.STRING).description("?????? ?????????").optional()
                        )
                ));
    }

}
