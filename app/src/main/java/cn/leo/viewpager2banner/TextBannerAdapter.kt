package cn.leo.viewpager2banner

/**
 * @author : ling luo
 * @date : 2019-12-25
 */
class TextBannerAdapter : LeoRvAdapter<String>() {
    override fun getItemLayout(position: Int): Int {
        return R.layout.item_text_banner
    }

    override fun bindData(helper: ItemHelper, data: String) {
        helper.setText(R.id.tvText, data)
    }
}