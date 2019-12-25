package cn.leo.viewpager2banner

/**
 * @author : ling luo
 * @date : 2019-12-25
 */
class BannerAdapter : LeoRvAdapter<Int>() {
    override fun getItemLayout(position: Int): Int {
        return R.layout.item_banner
    }

    override fun bindData(helper: ItemHelper, data: Int) {
        helper.setImageResource(R.id.image, data)
            .setText(R.id.tvIndex, helper.position.toString())
    }
}