package com.muesli.music.genre;

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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class GenreApiControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("genre/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("?????? ?????? ?????? TEST")
    public void retrieveGenreList() throws Exception {
        this.mockMvc.perform(get("/api/v1/genre")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("genre/{method-name}",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data[].image").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data[].displayName").type(JsonFieldType.STRING).description("?????? ?????? ??????").optional(),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("data[].albumList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data[].albumList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data[].albumList[].releaseDate").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data[].albumList[].originalName").type(JsonFieldType.STRING).description("?????? ??????(??????)").optional(),
                                fieldWithPath("data[].albumList[].image").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data[].albumList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data[].albumList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data[].albumList[].trackList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data[].albumList[].trackList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data[].albumList[].trackList[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data[].albumList[].trackList[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data[].albumList[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data[].albumList[].trackList[].image").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data[].albumList[].trackList[].adult").type(JsonFieldType.NUMBER).description("19??? ??????").optional(),
                                fieldWithPath("data[].albumList[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("???????????? ??????").optional(),
                                fieldWithPath("data[].albumList[].trackList[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data[].albumList[].trackList[].number").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data[].albumList[].trackList[].duration").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data[].albumList[].trackList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data[].albumList[].trackList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data[].albumList[].trackList[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data[].albumList[].trackList[].albumName").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ?????? TEST")
    public void retrieveGenre() throws Exception {
        this.mockMvc.perform(get("/api/v1/genre/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("genre/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("?????? IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].releaseDate").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.list[].originalName").type(JsonFieldType.STRING).description("?????? ??????(??????)").optional(),
                                fieldWithPath("data.list[].image").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.list[].trackList[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].trackList[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].trackList[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.list[].trackList[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.list[].trackList[].image").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.list[].trackList[].adult").type(JsonFieldType.NUMBER).description("19??? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("???????????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].trackList[].number").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].duration").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].trackList[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.list[].trackList[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.list[].trackList[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].trackList[].albumName").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ????????? ?????? TEST")
    public void retrieveGenreTracklist() throws Exception {
        this.mockMvc.perform(get("/api/v1/genre/{id}/track", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("genre/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("?????? IDX")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("????????? ??????"),
                                fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].albumImage").type(JsonFieldType.STRING).description("?????? ?????????").optional(),
                                fieldWithPath("data.list[].description").type(JsonFieldType.STRING).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].artistsLegacy").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.list[].image").type(JsonFieldType.STRING).description("?????? ????????????").optional(),
                                fieldWithPath("data.list[].adult").type(JsonFieldType.NUMBER).description("19??? ??????").optional(),
                                fieldWithPath("data.list[].isTitle").type(JsonFieldType.NUMBER).description("???????????? ??????").optional(),
                                fieldWithPath("data.list[].musicUrl").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.list[].number").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].duration").type(JsonFieldType.NUMBER).description("?????? ??????").optional(),
                                fieldWithPath("data.list[].artistId").type(JsonFieldType.NUMBER).description("???????????? IDX"),
                                fieldWithPath("data.list[].artistName").type(JsonFieldType.STRING).description("???????????? ??????"),
                                fieldWithPath("data.list[].albumId").type(JsonFieldType.NUMBER).description("?????? IDX"),
                                fieldWithPath("data.list[].albumName").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

}
