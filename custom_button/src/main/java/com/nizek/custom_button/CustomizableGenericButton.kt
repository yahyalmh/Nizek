import android.content.Context
import android.widget.FrameLayout

/**
 * @author yaya (@yahyalmh)
 * @since 30th December 2021
 */

class CustomizableGenericButton(context: Context) : FrameLayout(context) {
    private var iconId: Int = 0
    private var title: String? = null
    private var subTitle: String? = null

    init {
            
    }
    class Builder(private val context: Context) {
        private val params = DialogParams()

        fun setIcon(iconId: Int): Builder {
            params.mIconId = iconId
            return this
        }

        fun setTitle(title: String): Builder {
            params.mTitle = title
            return this
        }

        fun setSubTitle(subTitle: String): Builder {
            params.mSubTitle = subTitle
            return this
        }
//
//        fun setFirstOption(text: String, listener: OptionalDialogClickListener): Builder {
//            params.mFirstOption = text
//            params.mFirstOptionListener = listener
//            return this
//        }


        fun create(): CustomizableGenericButton {
            val button = CustomizableGenericButton(context)
            params.apply(button)
            return button
        }
    }

    class DialogParams {
        var mIconId: Int = 0
        var mTitle: String? = null
        var mSubTitle: String? = null

//        lateinit var mFirstOptionListener: OptionalDialogClickListener

        fun apply(dialog: CustomizableGenericButton) {
            dialog.iconId = mIconId
            dialog.title = mTitle
            dialog.subTitle = mSubTitle

//            dialog.firstOptionListener = mFirstOptionListener
        }
    }
}