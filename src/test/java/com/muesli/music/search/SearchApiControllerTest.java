package com.muesli.music.search;

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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MuonBackendV1Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class SearchApiControllerTest {
    private MockMvc mockMvc;
    private final String JWT_TOKEN = JwtToken.JWT_TOKEN;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("search/{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    @DisplayName("전체 검색 결과 페이지 조회 TEST")
    public void retrieveAllSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/search/all")
                        .param("size", "10")
                        .param("page", "1")
                        .param("keyword", "the")
                        .param("type", "similar")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("search/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("검색 결과 표시 개수"),
                                parameterWithName("page").description("검색 결과 페이지"),
                                parameterWithName("keyword").description("검색 키워드"),
                                parameterWithName("type").description("검색 타입")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.keyword").type(JsonFieldType.STRING).description("검색 키워드"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("검색 타입"),
                                fieldWithPath("data.track.keyword").type(JsonFieldType.STRING).description("트랙 검색 키워드"),
                                fieldWithPath("data.track.type").type(JsonFieldType.STRING).description("트랙 검색 타입"),
                                fieldWithPath("data.track.count").type(JsonFieldType.NUMBER).description("트랙 검색 결과 개수"),
                                fieldWithPath("data.track.trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.track.trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.track.trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.track.trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.track.trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.track.trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.track.trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.track.trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.track.trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.track.trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.track.trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.track.trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.track.trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.album.keyword").type(JsonFieldType.STRING).description("앨범 검색 키워드"),
                                fieldWithPath("data.album.type").type(JsonFieldType.STRING).description("앨범 검색 타입"),
                                fieldWithPath("data.album.count").type(JsonFieldType.NUMBER).description("앨범 검색 결과 개수"),
                                fieldWithPath("data.album.albumList[].id").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.album.albumList[].name").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.album.albumList[].releaseDate").type(JsonFieldType.STRING).description("앨범 발매일"),
                                fieldWithPath("data.album.albumList[].originalName").type(JsonFieldType.STRING).description("앨범 이름(원문)").optional(),
                                fieldWithPath("data.album.albumList[].image").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.album.albumList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.album.albumList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.album.albumList[].trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.album.albumList[].trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.album.albumList[].trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.album.albumList[].trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.album.albumList[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.album.albumList[].trackList[].image").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.album.albumList[].trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.album.albumList[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.album.albumList[].trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.album.albumList[].trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.album.albumList[].trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.album.albumList[].trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.album.albumList[].trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.album.albumList[].trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.album.albumList[].trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.artist.keyword").type(JsonFieldType.STRING).description("아티스트 검색 키워드 개수"),
                                fieldWithPath("data.artist.type").type(JsonFieldType.STRING).description("아티스트 검색 타입"),
                                fieldWithPath("data.artist.count").type(JsonFieldType.NUMBER).description("아티스트 검색 결과 개수"),
                                fieldWithPath("data.artist.artistList[].id").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.artist.artistList[].name").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.artist.artistList[].originalName").type(JsonFieldType.STRING).description("아티스트 이름(원문)").optional(),
                                fieldWithPath("data.artist.artistList[].englishName").type(JsonFieldType.STRING).description("아티스트 이름(영어)").optional(),
                                fieldWithPath("data.artist.artistList[].image").type(JsonFieldType.STRING).description("아티스트 이미지"),
                                fieldWithPath("data.artist.artistList[].birthday").type(JsonFieldType.STRING).description("아티스트 생일"),
                                fieldWithPath("data.artist.artistList[].country").type(JsonFieldType.STRING).description("아티스트 국가"),
                                fieldWithPath("data.artist.artistList[].debut").type(JsonFieldType.NUMBER).description("아티스트 데뷔년도")
                        )
                ));
    }

    @Test
    @DisplayName("트랙 검색 결과 페이지 TEST")
    public void retrieveTrackSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/search/track")
                        .param("size", "100")
                        .param("page", "1")
                        .param("keyword", "the")
                        .param("type", "similar")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("search/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("검색 결과 표시 개수"),
                                parameterWithName("page").description("검색 결과 페이지"),
                                parameterWithName("keyword").description("검색 키워드"),
                                parameterWithName("type").description("검색 타입")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.keyword").type(JsonFieldType.STRING).description("트랙 검색 키워드"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("트랙 검색 타입"),
                                fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("트랙 검색 결과 개수"),
                                fieldWithPath("data.trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("앨범 검색 결과 페이지 TEST")
    public void retrieveAlbumSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/search/album")
                        .param("size", "100")
                        .param("page", "1")
                        .param("keyword", "the")
                        .param("type", "similar")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("search/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("검색 결과 표시 개수"),
                                parameterWithName("page").description("검색 결과 페이지"),
                                parameterWithName("keyword").description("검색 키워드"),
                                parameterWithName("type").description("검색 타입")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.keyword").type(JsonFieldType.STRING).description("앨범 검색 키워드"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("앨범 검색 타입"),
                                fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("앨범 검색 결과 개수"),
                                fieldWithPath("data.albumList[].id").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.albumList[].name").type(JsonFieldType.STRING).description("앨범 이름"),
                                fieldWithPath("data.albumList[].releaseDate").type(JsonFieldType.STRING).description("앨범 발매일"),
                                fieldWithPath("data.albumList[].originalName").type(JsonFieldType.STRING).description("앨범 이름(원문)").optional(),
                                fieldWithPath("data.albumList[].image").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.albumList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.albumList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.albumList[].trackList[].id").type(JsonFieldType.NUMBER).description("트랙 IDX"),
                                fieldWithPath("data.albumList[].trackList[].name").type(JsonFieldType.STRING).description("트랙 이름"),
                                fieldWithPath("data.albumList[].trackList[].albumImage").type(JsonFieldType.STRING).description("앨범 이미지").optional(),
                                fieldWithPath("data.albumList[].trackList[].description").type(JsonFieldType.STRING).description("트랙 설명").optional(),
                                fieldWithPath("data.albumList[].trackList[].artistsLegacy").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.albumList[].trackList[].image").type(JsonFieldType.STRING).description("연관 아티스트").optional(),
                                fieldWithPath("data.albumList[].trackList[].adult").type(JsonFieldType.NUMBER).description("19금 여부").optional(),
                                fieldWithPath("data.albumList[].trackList[].isTitle").type(JsonFieldType.NUMBER).description("타이틀곡 여부").optional(),
                                fieldWithPath("data.albumList[].trackList[].musicUrl").type(JsonFieldType.STRING).description("트랙 경로"),
                                fieldWithPath("data.albumList[].trackList[].number").type(JsonFieldType.NUMBER).description("트랙 순서").optional(),
                                fieldWithPath("data.albumList[].trackList[].duration").type(JsonFieldType.NUMBER).description("트랙 길이").optional(),
                                fieldWithPath("data.albumList[].trackList[].artistId").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.albumList[].trackList[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.albumList[].trackList[].albumId").type(JsonFieldType.NUMBER).description("앨범 IDX"),
                                fieldWithPath("data.albumList[].trackList[].albumName").type(JsonFieldType.STRING).description("앨범 이름")
                        )
                ));
    }

    @Test
    @DisplayName("아티스트 검색 결과 페이지 TEST")
    public void retrieveArtistSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/search/artist")
                        .param("size", "100")
                        .param("page", "1")
                        .param("keyword", "the")
                        .param("type", "similar")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("search/{method-name}",
                        requestParameters(
                                parameterWithName("size").description("검색 결과 표시 개수"),
                                parameterWithName("page").description("검색 결과 페이지"),
                                parameterWithName("keyword").description("검색 키워드"),
                                parameterWithName("type").description("검색 타입")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data.keyword").type(JsonFieldType.STRING).description("아티스트 검색 키워드 개수"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("아티스트 검색 타입"),
                                fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("아티스트 검색 결과 개수"),
                                fieldWithPath("data.artistList[].id").type(JsonFieldType.NUMBER).description("아티스트 IDX"),
                                fieldWithPath("data.artistList[].name").type(JsonFieldType.STRING).description("아티스트 이름"),
                                fieldWithPath("data.artistList[].originalName").type(JsonFieldType.STRING).description("아티스트 이름(원문)").optional(),
                                fieldWithPath("data.artistList[].englishName").type(JsonFieldType.STRING).description("아티스트 이름(영어)").optional(),
                                fieldWithPath("data.artistList[].image").type(JsonFieldType.STRING).description("아티스트 이미지"),
                                fieldWithPath("data.artistList[].birthday").type(JsonFieldType.STRING).description("아티스트 생일"),
                                fieldWithPath("data.artistList[].country").type(JsonFieldType.STRING).description("아티스트 국가"),
                                fieldWithPath("data.artistList[].debut").type(JsonFieldType.NUMBER).description("아티스트 데뷔년도")
                        )
                ));
    }

    @Test
    @DisplayName("키워드 자동 완성 TEST")
    public void searchKeywordList() throws Exception {
        this.mockMvc.perform(get("/api/v1/search")
                        .param("keyword", "the")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("search/{method-name}",
                        requestParameters(
                                parameterWithName("keyword").description("검색 키워드")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메세지").optional(),
                                fieldWithPath("errorCode").type(JsonFieldType.STRING).description("결과 코드").optional(),
                                fieldWithPath("data[].keyword").type(JsonFieldType.STRING).description("검색 키워드 결과")
                        )
                ));
    }

}
