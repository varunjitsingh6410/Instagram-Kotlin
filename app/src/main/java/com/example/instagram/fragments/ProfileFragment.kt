package com.example.instagram.fragments

import android.util.Log
import com.example.instagram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : FeedFragment() {

    private val TAG = "ProfileFragment"

    override fun queryPosts()
    {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null)
                {
                    Log.e(TAG, "Error during post query")
                    e.printStackTrace()
                } else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(
                                TAG, "Post Query results: " + post.getDescription()
                                        + ", username: " + post.getUser()?.username )
                        }
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}