package com.jj.sample.showcase

import android.os.Bundle
import android.view.LayoutInflater
import com.jj.sample.databinding.ActivityUiDemoBinding
import com.jj.ui.base.JiangToolbarStateVmActivity
import com.jj.ui.dialog.JInputDialog
import com.jj.ui.dialog.JListDialog
import com.jj.ui.dialog.JMultiChoiceDialog
import com.jj.ui.dialog.JScanInputDialog
import com.jj.ui.dialog.JSingleChoiceDialog
import com.jj.ui.ext.clickNoRepeat

class UiDemoActivity : JiangToolbarStateVmActivity<ActivityUiDemoBinding, ShowcaseViewModel>() {

    private var noRepeatClickCount = 0

    override fun createViewBinding(inflater: LayoutInflater): ActivityUiDemoBinding {
        return ActivityUiDemoBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnToast.setOnClickListener {
            showToast("Toast 示例")
        }

        binding.btnLoadingDialog.setOnClickListener {
            showLoadingDialog("加载中...")
            binding.root.postDelayed(
                {
                    dismissLoadingDialog()
                    showToast("LoadingDialog 已关闭")
                },
                LOADING_DIALOG_DELAY_MS,
            )
        }

        binding.btnConfirmDialog.setOnClickListener {
            showConfirmDialog(
                title = "ConfirmDialog 示例",
                message = "请选择一个操作",
                onConfirm = {
                    binding.tvResult.text = "点击了确定"
                    showToast("点击了确定")
                },
                onCancel = {
                    binding.tvResult.text = "点击了取消"
                    showToast("点击了取消")
                },
            )
        }

        binding.btnInputDialog.setOnClickListener {
            JInputDialog(this, this)
                .setTitle("输入框示例")
                .setHint("请输入内容")
                .setOnConfirm { value ->
                    binding.tvResult.text = "输入内容: $value"
                }
                .show()
        }

        binding.btnScanInputDialog.setOnClickListener {
            JScanInputDialog(this, this)
                .setTitle("扫码框示例")
                .setHint("请扫码或手动输入")
                .setOnSubmit { code ->
                    binding.tvResult.text = "扫码内容: $code"
                }
                .show()
        }

        binding.btnSingleChoiceDialog.setOnClickListener {
            JSingleChoiceDialog<String>(this, this)
                .setTitle("单选示例")
                .setItems(SAMPLE_ITEMS)
                .setSelectedIndex(1)
                .setOnConfirm { value ->
                    binding.tvResult.text = "单选结果: ${value.orEmpty()}"
                }
                .show()
        }

        binding.btnMultiChoiceDialog.setOnClickListener {
            JMultiChoiceDialog<String>(this, this)
                .setTitle("多选示例")
                .setItems(SAMPLE_ITEMS)
                .setSelectedIndexes(setOf(0, 2))
                .setOnConfirm { values ->
                    binding.tvResult.text = "多选结果: ${values.joinToString()}"
                }
                .show()
        }

        binding.btnListDialog.setOnClickListener {
            JListDialog<String>(this, this)
                .setTitle("列表示例")
                .setItems(SAMPLE_ITEMS)
                .setOnItemClick { value ->
                    binding.tvResult.text = "列表点击: $value"
                }
                .show()
        }

        binding.btnNoRepeat.clickNoRepeat {
            noRepeatClickCount += 1
            binding.tvResult.text = "防重复点击次数: $noRepeatClickCount"
            showToast("防重复点击生效")
        }
    }

    override fun toolbarTitle(): CharSequence = "UI 示例"

    override fun enableStateLayout(): Boolean = false

    private companion object {
        const val LOADING_DIALOG_DELAY_MS = 1200L
        val SAMPLE_ITEMS = listOf("仓库 A", "仓库 B", "仓库 C")
    }
}
