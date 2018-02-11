package com.danielakinola.loljournal.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.danielakinola.loljournal.ChampionReference;

import java.util.Locale;

//TODO: add more constructors

@Entity(tableName = "ChampPool", indices = {@Index(value = {"id"}, unique = true), @Index(value = {"name", "lane"}, unique = true)})
public class Champion {

    @PrimaryKey
    @NonNull
    private final String id;
    private final String name;
    private final int lane;
    @Ignore
    private final int imageResource;
    private boolean starred;

    public Champion(@NonNull String id, @NonNull String name, int lane, boolean starred) {
        this.id = String.format(Locale.ENGLISH, "%s_%d", name, lane);
        this.name = name;
        this.imageResource = ChampionReference.champions.get(name);
        this.lane = lane;
        this.starred = starred;
    }

    @Ignore
    public Champion(@NonNull String name, int lane, boolean starred) {
        this.id = String.format(Locale.ENGLISH, "%s_%d", name, lane);
        this.name = name;
        this.imageResource = ChampionReference.champions.get(name);
        this.lane = lane;
        this.starred = starred;
    }

    @Ignore
    public Champion(@NonNull String name, int lane) {
        this.id = String.format(Locale.ENGLISH, "%s_%d", name, lane);
        this.name = name;
        this.imageResource = ChampionReference.champions.get(name);
        this.lane = lane;
        this.starred = false;
    }

    @Ignore
    public Champion(String id, @NonNull String name, int lane) {
        this.id = id;
        this.name = name;
        this.imageResource = ChampionReference.champions.get(name);
        this.lane = lane;
        this.starred = false;
    }

    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getLane() {
        return lane;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }
}