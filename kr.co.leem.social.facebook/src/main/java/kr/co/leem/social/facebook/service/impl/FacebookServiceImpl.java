package kr.co.leem.social.facebook.service.impl;

import java.util.ArrayList;
import java.util.List;

import kr.co.leem.social.facebook.service.FacebookService;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.Photo;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

/**
 * Spring social Facebook.
 * 
 * @author Administrator
 *
 */
@Service
public class FacebookServiceImpl implements FacebookService {
	
//	@SuppressWarnings("unused")
//	private static final Logger logger = LoggerFactory.getLogger(FacebookServiceImpl.class);
	
	private Facebook facebook;
	
	public void setAccessToken(String accessToken) throws Exception {
		facebook = new FacebookTemplate(accessToken);
	}
	 
	@Override
	public FacebookProfile getUserProfile() throws Exception {
		return facebook.userOperations().getUserProfile();
	}
	
	@Override
	public List<Album> getAlbums() throws Exception {
		return facebook.mediaOperations().getAlbums();
	}
	
	@Override
	public Album getAlbum(String albumId) throws Exception {
		return facebook.mediaOperations().getAlbum(albumId);
	}
	
	@Override
	public List<Photo> getPhotos(String albumId) throws Exception {
		return facebook.mediaOperations().getPhotos(albumId, 0, 1000000000);
	}
	
	@Override
	public byte[] getPhotoImage(String photoId) throws Exception {
		return facebook.mediaOperations().getAlbumImage(photoId);
	}
	
	public List<Photo> getAlbumPhotos() throws Exception {
		List<Album> albums = getAlbums();
		List<Photo> results = new ArrayList<Photo>();
		
		for (int i = 0; i < albums.size(); i++) {
			List<Photo> photos = getPhotos(albums.get(i).getId());
			for (int j = 0; j < photos.size(); j++) {
				results.add(photos.get(j));
			}
		}
		
		return results;
	}
}