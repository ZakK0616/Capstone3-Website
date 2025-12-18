package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile getByUserId(int userId);
    void update(int userId, Profile profile);
    Profile create(Profile profile);
}
