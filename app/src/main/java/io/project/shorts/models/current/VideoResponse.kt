package io.project.shorts.models.current

import com.google.gson.annotations.SerializedName

data class VideoResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Creator(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("handle")
	val handle: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("pic")
	val pic: String? = null
)

data class Data(

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("posts")
	val posts: List<PostsItem?>? = null
)

data class Comment(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("commentingAllowed")
	val commentingAllowed: Boolean? = null
)

data class Submission(

	@field:SerializedName("hyperlink")
	val hyperlink: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("mediaUrl")
	val mediaUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("placeholderUrl")
	val placeholderUrl: String? = null
)

data class Reaction(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("voted")
	val voted: Boolean? = null
)

data class PostsItem(

	@field:SerializedName("creator")
	val creator: Creator? = null,

	@field:SerializedName("reaction")
	val reaction: Reaction? = null,

	@field:SerializedName("comment")
	val comment: Comment? = null,

	@field:SerializedName("submission")
	val submission: Submission? = null,

	@field:SerializedName("postId")
	val postId: String? = null
)
