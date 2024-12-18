package com.example.myrooms;
import javafx.scene.media.AudioClip;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicPlayer implements Serializable {

    private ArrayList<Music> listOfAllMusics;
    private ArrayList<Music> listOfNotLockedMusics;
    private ArrayList<Music> listOfLockedMusics;
    private Music currentMusic;
    private int indexOfCurrentMusic;
    Music first,second,third,fourth,fifth,sixth,seventh;

    public MusicPlayer() {

        listOfAllMusics = new ArrayList<>();
        listOfNotLockedMusics = new ArrayList<>();
        listOfLockedMusics = new ArrayList<>();

        first = new Music(0);
        first.setName("Library Sound");
        second = new Music(1);
        second.setName("Nature Sound");
        third = new Music(2);
        third.setName("Rain Sound");
        fourth = new Music(3);
        fourth.setName("Relaxing Sound");
        fifth = new Music(4);
        fifth.setName("Godfather Theme");
        fifth.lock();
        sixth = new Music(5);
        sixth.setName("Cello And Piano");
        sixth.lock();
        seventh = new Music(6);
        seventh.setName("Loft Music");
        seventh.lock();

        listOfAllMusics.add(first);
        listOfAllMusics.add(second);
        listOfAllMusics.add(third);
        listOfAllMusics.add(fourth);
        listOfAllMusics.add(fifth);
        listOfAllMusics.add(sixth);
        listOfAllMusics.add(seventh);
        listOfLockedMusics.add(fifth);
        listOfLockedMusics.add(sixth);
        listOfLockedMusics.add(seventh);
        listOfNotLockedMusics.add(first);
        listOfNotLockedMusics.add(second);
        listOfNotLockedMusics.add(third);
        listOfNotLockedMusics.add(fourth);
    }

    public void setCurrentMusic(String name) {
        for (int i = 0; i < this.getListOfAllMusics().size(); i++) {
            if (this.getListOfAllMusics().get(i).getName().equals(name)) {
                this.currentMusic = this.getListOfAllMusics().get(i);
            }
        }
    }
    public void setCurrentMusic(Music music){
        this.currentMusic = music;
    }

    public ArrayList<Music> getListOfNotLockedMusics()
    {
        return this.listOfNotLockedMusics;
    }
    public ArrayList<Music> getListOfLockedMusics()
    {
        return this.listOfLockedMusics;
    }

    public ArrayList<Music> getListOfAllMusics() {
        return listOfAllMusics;
    }

    public Music getCurrentMusic() {
        return this.currentMusic;
    }

    public void unlockMusic(Music music)
    {
        if(music.getLockSituation())
        {
            music.unlock();
            this.listOfNotLockedMusics.add(music);
            listOfLockedMusics.remove(music);
        }
    }
    public int getIndexInNotLockedSong()
    {
        if(currentMusic!=null)
        {
            return listOfNotLockedMusics.indexOf(currentMusic);
        }
        return 0;
    }
}

