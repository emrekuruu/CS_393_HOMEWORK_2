
const playlists = [];

async function searchSong(){
    var searched_song = document.getElementById("search").value
    var table = document.querySelector("#songTable");
    clearTable(table);
    var second_list;
    var url = "http://localhost:8080/songs/?name="+searched_song;
    axios.get(url)
        .then(res => {
                const songs = (res.data);
                second_list = JSON.parse(JSON.stringify(songs))
                console.log(second_list)
                for(const song of second_list){

                    const row = document.createElement("tr");
                    const checkBox = document.createElement("input");
                    checkBox.type = "checkbox";
                    checkBox.id = "checkBox"
                    row.appendChild(checkBox);

                    const songName = document.createElement("td");
                    songName.innerHTML = song.name;
                    const artistName = document.createElement("td");
                    artistName.innerHTML = song.artist;
                    const albumName = document.createElement("td");
                    albumName.innerHTML = song.album;
                    const length = document.createElement("td");
                    length.innerHTML = song.length

                    row.appendChild(artistName);
                    row.appendChild(songName);
                    row.appendChild(albumName);
                    row.appendChild(length);

                    table.appendChild(row);
                }
            }
        )

}

function clearTable(table) {
    var rows = table.rows;
    var i = rows.length;
    while (--i) {
        rows[i].parentNode.removeChild(rows[i]);
        // or
        // table.deleteRow(i);
    }
}

function addPlaylist() {
    try {
        var table = document.querySelector("#songTable");
        var name = document.getElementById("create").value;
        if(name.length < 5){
            throw Error;
        }
        else if(table.rows.length < 2){
            throw Error;
        }

        var playlist = {};
        var length = 0;
        var count = 0;

        for (let i = 1; i < table.rows.length; i++) {
            var rows = table.rows;
            if (rows[i].firstChild.checked) {
                count++;
                length += parseFloat(rows[i].children[4].innerHTML);
            }
        }

        var min = Math.floor(length / 60);
        var secs = length % 60;

        length = "" + min + ":" + secs;

        playlist["Name"] = name;
        playlist["Length"] = length;
        playlist["Count"] = count;

        playlists.push(playlist);
        console.log(playlists);
        for (let i = 0; i < playlists.length; i++) {
            localStorage.setItem("playlistsLength", playlists.length);
            localStorage.setItem("name" + i, playlists[i].Name);
            localStorage.setItem("length" + i, playlists[i].Length);
            localStorage.setItem("count" + i, playlists[i].Count);
        }
        document.getElementById("Succes").innerHTML = "Playlist created successfully"
    }
    catch(error){
        document.getElementById("Succes").innerHTML = "Playlist was not created"
    }

    //now send the playlist back to the database
    var post_url = "http://localhost:8080/add/playlist/?name=";
    post_url += name;
    post_url += "&songNames="
    for (let i = 1; i < table.rows.length; i++) {
        var rows_2 = table.rows;
        if (rows_2[i].firstChild.checked) {
            post_url += (rows_2[i].children[2].innerHTML);
            post_url += ","
        }
    }

    post_url = post_url.slice(0,post_url.length-1)

    axios.post(post_url)
        .then(res => {
            console.log(res);
            console.log(res.data);
        })

}


function fillPlaylistTable(){

    var l = localStorage.getItem("playlistsLength");

    var table2 = document.querySelector("#playlistTable");
    for(let i = 0; i < l; i++){

        const row = document.createElement("tr");

        const Name = document.createElement("td");
        Name.innerHTML = localStorage.getItem("name"+i)

        const Length = document.createElement("td")
        Length.innerHTML = localStorage.getItem("length"+i)


        const Count = document.createElement("td")
        Count.innerHTML = localStorage.getItem("count"+i)

        row.appendChild(Name);
        row.appendChild(Count);
        row.appendChild(Length);

        table2.appendChild(row);
    }
}

