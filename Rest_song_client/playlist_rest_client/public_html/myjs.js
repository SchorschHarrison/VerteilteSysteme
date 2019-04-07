function makeSongDiv(results, ergebnisDiv){
  results.forEach(result => {
      let html = `
          <div>
              <b>Id:</b> ${result.id} <br/>
              <b>Title:</b> ${result.title} <br/>
              <b>Artist:</b> ${result.artist} <br/>
              <b>Spotify ID:</b> ${result.spotifyId} <br/>
              <b>Playlist:</b> ${result.playlistName} <br/>
              <b>Owner:</b> ${result.username} <br/>
          </div>
      `;


      ergebnisDiv.innerHTML += html;
  });
}

function makePlaylistDiv(results, ergebnisDiv){
  results.forEach(result => {
      let html = `
          <div>
              <b>Id:</b> ${result.id} <br/>
              <b>Name:</b> ${result.playlist_name} <br/>
              <b>Owner:</b> ${result.username} <br/>
          </div>
      `;




      ergebnisDiv.innerHTML += html;
  });


}

function makeUserDiv(results, ergebnisDiv){
  results.forEach(result => {
      let html = `
          <div>
              <b>Username:</b> ${result.username} <br/>
              <b>Vorname:</b> ${result.firstname} <br/>
              <b>Nachname:</b> ${result.lastname} <br/>
          </div>
      `;


      ergebnisDiv.innerHTML += html;
  });


}
