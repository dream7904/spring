package kr.co.leem.social.facebook.service;

import java.util.List;

import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.Photo;

public interface FacebookService {
	/**
	 * AccessToken 설정.
	 * 
	 * @param accessToken
	 * @throws Exception
	 */
	public void setAccessToken(String accessToken) throws Exception;
	
	/**
	 * 사용자 프로파일 정보.
	 * 
	 * @return
	 * @throws Exception
	 */
	public FacebookProfile getUserProfile() throws Exception;
	
	/**
	 * 사용자 앨범 목록.
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Album> getAlbums() throws Exception;
	
	/**
	 * 사용자 앨범.
	 * @param albumId
	 * @return
	 * @throws Exception
	 */
	public Album getAlbum(String albumId) throws Exception;
	
	/**
	 * 앨범 내의 사용자 사진 목록.
	 * 
	 * @param albumId
	 * @return
	 * @throws Exception
	 */
	public List<Photo> getPhotos(String albumId) throws Exception;
	
	/**
	 * 사진 이미지.
	 * 
	 * @param photoId
	 * @return
	 * @throws Exception
	 */
	public byte[] getPhotoImage(String photoId) throws Exception;
	
	/**
	 * 모든 앨범의 이미지.
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Photo> getAlbumPhotos() throws Exception;
}
