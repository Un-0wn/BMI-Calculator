package com.D0st.bmicalc

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.D0st.bmicalc.databinding.FragmentHomeBinding
import com.cncoderx.wheelview.OnWheelChangedListener

import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager


class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private lateinit var weightAdapter: WeightPickerAdapter
    private var gender = 'M'
    var height = 1
    private var weight = 50

    private var doubleBackToExitPressedOnce = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationView()
//        Gender
        val titlesOfGender: List<String> = listOf("F", "O", "M")

        binding.genderWheelView.apply {
            titles = titlesOfGender
            elevation = 0f
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                isFocusedByDefault = true
            }
            isSelected = true
            focusedIndex = 2
        }
        binding.genderWheelView.selectListener = {
            gender = titlesOfGender[it][0]
        }


//        Weight
        val pickerLayoutManager = PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.apply {
            isChangeAlpha = true
            scaleDownBy = 0.99f
            scaleDownDistance = 0.8f
            initialPrefetchItemCount = 3
            isSmoothScrollbarEnabled = true
            scrollToPosition(49)
        }


        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.weightRecyclerBtn)

        weightAdapter =
            WeightPickerAdapter(requireContext(), getData(151), binding.weightRecyclerBtn)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.weightRecyclerBtn.defaultFocusHighlightEnabled = true
        }
        binding.weightRecyclerBtn.apply {
            layoutManager = pickerLayoutManager
            adapter = weightAdapter
            isSelected = true
            requestFocus()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                isFocusedByDefault = true
            }

        }


        pickerLayoutManager.setOnScrollStopListener { view ->
            weight = Integer.parseInt((view as TextView).text.toString())
        }


//        Height
        binding.heightWheel.onWheelChangedListener =
            OnWheelChangedListener { view, _, newIndex ->
                val text = view.getItem(newIndex)
                height = Integer.parseInt(text.toString())
//                Toast.makeText(this,""+ text,Toast.LENGTH_SHORT).show()

            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.heightWheel.apply {
                defaultFocusHighlightEnabled = true

            }
        }

        binding.startButton.setOnActiveListener {
            animationViewUp()
            binding.startButton.alpha = 0f
            Handler().postDelayed({
                gotoSecond()
//                val intent = Intent(this,ResultActivity::class.java)
//                intent.putExtra("Height",height.toDouble())
//                intent.putExtra("Weight",weight.toDouble())
//                if(gender == 'M'){
//                    intent.putExtra("Gender",0)
//                }
//                else{
//                    intent.putExtra("Gender",1)
//                }
//                startActivity(intent)

            }, 500)
        }

    }

    private fun gotoSecond () {

        val action = HomeFragmentDirections.actionFirstFragmentToSecondFragment(
            weight = weight,
            height = height,
            gender = if (gender == 'M') {
                0
            } else {
                1
            }
        )
        findNavController().safeNavigate(action)
    }

    private fun getData(count: Int): List<String> {
        val data: MutableList<String> = ArrayList()
        for (i in 0 until count) {
            data.add(i.toString())
        }
        return data
    }

    private fun animationView() {

        binding.apply {

            bodyContainer.translationY = 150f
            footerContainer?.translationY = 150f
            heightWheel.translationY = 150f
            weightRecyclerBtn.translationX = 150f


            bodyContainer.alpha = 0f
            footerContainer?.alpha = 0f
            heightWheel.alpha = 0f
            weightRecyclerBtn.alpha = 0f



            bodyContainer.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(300)
                .start()
            footerContainer?.animate()?.translationY(0f)?.alpha(1f)?.setDuration(500)?.setStartDelay(400)
                ?.start()
            heightWheel.animate().translationY(0f).alpha(1f).setDuration(500).setStartDelay(450)
                .start()
            weightRecyclerBtn.animate().translationX(0f).alpha(1f).setDuration(500)
                .setStartDelay(500).start()

        }
    }

    private fun animationViewUp() {

        binding.apply {

            textView.animate().translationY(0f).alpha(0f).setDuration(50).setStartDelay(0).start()
            bodyContainer.animate().translationY(-250f).alpha(0f).setDuration(500).setStartDelay(0)
                .start()
            footerContainer?.animate()?.translationY(-250f)?.alpha(0f)?.setDuration(500)
                ?.setStartDelay(50)?.start()
            heightWheel.animate().translationY(-250f).alpha(0f).setDuration(500).setStartDelay(100)
                .start()
            weightRecyclerBtn.animate().translationX(-250f).alpha(0f).setDuration(500)
                .setStartDelay(150).start()

        }
    }

//    override fun onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed()
//            return
//        }
//
//        this.doubleBackToExitPressedOnce = true
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
//
//        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
//    }

}