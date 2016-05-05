package com.natureseyes.birdtheatre.service;

import com.natureseyes.birdtheatre.domain.BroadcastStream;
import com.natureseyes.birdtheatre.repository.BroadcastStreamRepository;

import com.natureseyes.birdtheatre.repository.CustomSocialUsersConnectionRepository;
import com.natureseyes.birdtheatre.repository.SocialUserConnectionRepository;
import com.natureseyes.birdtheatre.domain.SocialUserConnection;

import com.natureseyes.birdtheatre.repository.search.BroadcastStreamSearchRepository;
import com.natureseyes.birdtheatre.repository.search.UserSearchRepository;
import com.natureseyes.birdtheatre.security.SecurityUtils;
import com.natureseyes.birdtheatre.domain.CameraConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;
/*
 * Authentication
 */
//import com.bafconsulting.natureseyes.security.SecurityUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.google.api.Google;

/*
 * Social User Connection get API key
 */
import com.natureseyes.birdtheatre.domain.SocialUserConnection;

/**
 * Youtube API imports
 */
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.natureseyes.birdtheatre.domain.SocialUserConnection;
/**
 * Service Implementation for managing YoutubeBroadcasts. We can insert a youtube broadcast to schedule a broadcast.
 * We can update a broadcasts date, and we can delete scheduled broadcasts. When a broadcast is created it will trigger a script on
 * the rasbnerry pi to feed the cameras RTSP or RTMP video feed to youtubes RTMP video feed with the users unique broadcast ID.
 */
@Service
@Transactional
public class BroadcastStreamService {

    private final Logger log = LoggerFactory.getLogger(BroadcastStreamService.class);
    
    @Inject
    private BroadcastStreamRepository broadcastStreamRepository;
    

	
    
    private SocialUserConnection socialUserConnection;
    
	
	//private DateTime startDateTime;
    
	//private DateTime endDateTime;
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;
    
    @Inject
    private BroadcastStreamSearchRepository broadcastStreamSearchRepository;
    
    /**
     * Save a broadcastStream.
     * 
     * @param broadcastStream the entity to save
     * @return the persisted entity
     */
    public BroadcastStream save(BroadcastStream broadcastStream) {
        log.debug("Request to save BroadcastStream : {}", broadcastStream);
        
           BroadcastStream result = broadcastStream;
        
           //ZonedDateTime startDateTime = result.getStartTimestamp();
           //Date.from(result.getStartTimestamp().toInstant());
           //Date startDate = new Date(startDateTime.());
           //log.debug("the date" + startDate.toString());
           
           //DateTime endDateTime = result.getEndTimestamp();
          // Date endDate = new Date(endDateTime.getValue());
           //log.debug("the date" + endDate.toString());
              
           java.util.Calendar cal = Calendar.getInstance();
           //java.util.Date utilDate = startDate; // your util date
           //cal.setTime(startDate);
           cal.set(Calendar.HOUR_OF_DAY, result.getStartTimestamp().getHour());
           cal.set(Calendar.MINUTE, result.getStartTimestamp().getMinute());
           cal.set(Calendar.SECOND, result.getStartTimestamp().getSecond());
           cal.set(Calendar.MILLISECOND, (result.getStartTimestamp().getNano())/1000);   
           
           java.util.Calendar cal1 = Calendar.getInstance();
           //java.util.Date utilDate = startDate; // your util date
           //cal1.setTime(endDate);
           cal1.set(Calendar.HOUR_OF_DAY, result.getEndTimestamp().getHour());
           cal1.set(Calendar.MINUTE, result.getEndTimestamp().getMinute());
           cal1.set(Calendar.SECOND, result.getEndTimestamp().getSecond());
           cal1.set(Calendar.MILLISECOND, (result.getEndTimestamp().getNano())/1000);  
           
           java.sql.Date sqlStartDate = new java.sql.Date(cal.getTime().getTime()); // your sql date
           //java.sql.Date sqlDate = new java.sql.Date(startDateTime.getValue());
           log.debug("start date " + sqlStartDate.toString());
          
           java.sql.Date sqlEndDate = new java.sql.Date(cal1.getTime().getTime()); // your sql date
           //java.sql.Date sqlEndDate = new java.sql.Date(endDateTime.getValue());
           log.debug("end date " + sqlEndDate.toString());   
           
	       // This OAuth 2.0 access scope allows for full read/write access to the
	       // authenticated user's account. https://www.googleapis.com/auth/youtube It must be added to the users permissions url on signin
           //This can be done by modifying the angularjs file for webapps=>scripts=>account=>social=>social.service
	       //List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

        try {
        	String userId = SecurityUtils.getCurrentUserLogin();
        	 // This object is used to make YouTube Data API requests.
        	Connection<Google> connection = SecurityUtils.connection;
        	ConnectionData connectionData = connection.createData();
        	String accessToken = connectionData.getAccessToken();
        	GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        	Collection<String> currentScopes = credential.getServiceAccountScopes();

        	log.debug("currentScopes: " + currentScopes);
        	
        	//credential.
        	//List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        	//List<String> scopes = Lists.newArrayList("createbroadcast");
        	log.debug("accessToken {"+ accessToken + "}");
        	//scopes.add("https://www.googleapis.com/auth/plus.login");
        	// int i =0;
        	//while ( i < scopes.size()) {
        	//	System.out.println("Scope " + i + " " + scopes.get(i));
        	//	i++;
        	// }
        	
        	log.debug("Scopes" + credential.getServiceAccountScopes());
        	//credential.set
        	//new NetHttpTransport(), JacksonFactory.getDefaultInstance()
            youtube = new YouTube.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                   .setApplicationName("Test111").build();

            
            // Plus plus = new Plus.builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
            //     .setApplicationName("Google-PlusSample/1.0")
            //   .build();
            
            log.debug("Appname" + youtube.getApplicationName());
            // Prompt the user to enter a title for the broadcast.
            String title = broadcastStream.getName();
            String description = broadcastStream.getDescription();	
           // (System.out.println"You chose " + title + " for broadcast title.");
            youtube.getApplicationName();
            // Create a snippet with the title and scheduled start and end
            // times for the broadcast. Currently, those times are hard-coded.
            LiveBroadcastSnippet broadcastSnippet = new LiveBroadcastSnippet();
            broadcastSnippet.setTitle(title);
            broadcastSnippet.setDescription(description);
           
            /**
             * The date and time that the broadcast is scheduled to start. The value is specified in ISO 8601
             * (YYYY-MM-DDThh:mm:ss.sZ) format.
             * 2016-03-04T00:00:00.000Z
             * The value may be {@code null}.
             */
             
            log.debug("start time: " + result.getStartTimestamp());
            log.debug("end time: " + result.getEndTimestamp());
            //broadcastSnippet.setScheduledStartTime(result.getStartTimestamp());
            //broadcastSnippet.setScheduledEndTime(result.getEndTimestamp());
            //DateTime startDate = 
            //broadcastSnippet.setScheduledStartTime ("2016-03-04T00:00:00.000Z");
            
            broadcastSnippet.setScheduledStartTime(new DateTime("2024-01-30T00:00:00.000Z"));
            broadcastSnippet.setScheduledEndTime(new DateTime("2024-01-31T00:00:00.000Z"));
            
            // Set the broadcast's privacy status to "private". See:
            // https://developers.google.com/youtube/v3/live/docs/liveBroadcasts#status.privacyStatus
            LiveBroadcastStatus status = new LiveBroadcastStatus();
            status.setPrivacyStatus("public");

            LiveBroadcast broadcast = new LiveBroadcast();
            broadcast.setKind("youtube#liveBroadcast");
            broadcast.setSnippet(broadcastSnippet);
            broadcast.setStatus(status);

            // Construct and execute the API request to insert the broadcast.
            YouTube.LiveBroadcasts.Insert liveBroadcastInsert =
                    youtube.liveBroadcasts().insert("snippet,status", broadcast);
            LiveBroadcast returnedBroadcast = liveBroadcastInsert.execute();

            // Print information from the API response.
            System.out.println("\n================== Returned Broadcast ==================\n");
            System.out.println("  - Id: " + returnedBroadcast.getId());
            System.out.println("  - Title: " + returnedBroadcast.getSnippet().getTitle());
            System.out.println("  - Description: " + returnedBroadcast.getSnippet().getDescription());
            System.out.println("  - Published At: " + returnedBroadcast.getSnippet().getPublishedAt());
            System.out.println(
                    "  - Scheduled Start Time: " + returnedBroadcast.getSnippet().getScheduledStartTime());
            System.out.println(
                    "  - Scheduled End Time: " + returnedBroadcast.getSnippet().getScheduledEndTime());

            // Prompt the user to enter a title for the video stream.
            //title = getStreamTitle();
            title = broadcastStream.getName();
            //System.out.println("You chose " + title + " for stream title.");
            description = broadcastStream.getDescription();
            // Create a snippet with the video stream's title.
            LiveStreamSnippet streamSnippet = new LiveStreamSnippet();
            streamSnippet.setTitle(title);
            streamSnippet.setTitle(description);
            // Define the content distribution network settings for the
            // video stream. The settings specify the stream's format and
            // ingestion type. See:
            // https://developers.google.com/youtube/v3/live/docs/liveStreams#cdn
            CdnSettings cdnSettings = new CdnSettings();
            cdnSettings.setFormat("1080p");
            cdnSettings.setIngestionType("rtmp");

            LiveStream stream = new LiveStream();
            stream.setKind("youtube#liveStream");
            stream.setSnippet(streamSnippet);
            stream.setCdn(cdnSettings);

            // Construct and execute the API request to insert the stream.
            YouTube.LiveStreams.Insert liveStreamInsert =
                    youtube.liveStreams().insert("snippet,cdn", stream);
            LiveStream returnedStream = liveStreamInsert.execute();

            // Print information from the API response.
            System.out.println("\n================== Returned Stream ==================\n");
            System.out.println("  - Id: " + returnedStream.getId());
            System.out.println("  - Title: " + returnedStream.getSnippet().getTitle());
            System.out.println("  - Description: " + returnedStream.getSnippet().getDescription());
            System.out.println("  - Published At: " + returnedStream.getSnippet().getPublishedAt());

            // Construct and execute a request to bind the new broadcast
            // and stream.
            YouTube.LiveBroadcasts.Bind liveBroadcastBind =
                    youtube.liveBroadcasts().bind(returnedBroadcast.getId(), "id,contentDetails");
            liveBroadcastBind.setStreamId(returnedStream.getId());
            returnedBroadcast = liveBroadcastBind.execute();

            // Print information from the API response.
            System.out.println("\n================== Returned Bound Broadcast ==================\n");
            System.out.println("  - Broadcast Id: " + returnedBroadcast.getId());
            System.out.println(
                    "  - Bound Stream Id: " + returnedBroadcast.getContentDetails().getBoundStreamId());

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
       //CameraConfig tester = broadcastStream.getCameraConfigs();
        
       SSH2ConnectService ssh2connect = new SSH2ConnectService();
                ssh2connect.sshConnect();
        //embedHtml
        //youtubeBroadcastSearchRepository.save(result);
        //youtubeBroadcastRepository.save(result);
        //youtubeBroadcastSearchRepository.save(result);
                
        result = broadcastStreamRepository.save(broadcastStream);
        broadcastStreamSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the broadcastStreams.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<BroadcastStream> findAll(Pageable pageable) {
        log.debug("Request to get all BroadcastStreams");
        Page<BroadcastStream> result = broadcastStreamRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one broadcastStream by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public BroadcastStream findOne(Long id) {
        log.debug("Request to get BroadcastStream : {}", id);
        BroadcastStream broadcastStream = broadcastStreamRepository.findOne(id);
        return broadcastStream;
    }

    /**
     *  Delete the  broadcastStream by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BroadcastStream : {}", id);
        broadcastStreamRepository.delete(id);
        broadcastStreamSearchRepository.delete(id);
    }

    /**
     * Search for the broadcastStream corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BroadcastStream> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BroadcastStreams for query {}", query);
        return broadcastStreamSearchRepository.search(queryStringQuery(query), pageable);
    }
}
