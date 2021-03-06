/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2020, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 */

package org.isel.boardstar.dto;

import java.util.List;

//SUBSTITUIR POR STREAMS

public class GameDto {
    private final String id;
    private final String name;
    private final int year_published;
    private final String description;
    private final List<CategoryDto> categories;
    private final List<String> artists;

    public GameDto(String id, String name, int year_published, String description, List<CategoryDto> categories, List<String> artists) {
        this.id = id;
        this.name = name;
        this.year_published = year_published;
        this.description = description;
        this.categories = categories;
        this.artists = artists;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYearPublished() {
        return year_published;
    }

    public String getDescription() {
        return description;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public List<String> getArtists() {
        return artists;
    }
}
