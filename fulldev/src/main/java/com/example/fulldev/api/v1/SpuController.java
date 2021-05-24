package com.example.fulldev.api.v1;

import com.example.fulldev.bo.PageCounter;
import com.example.fulldev.exception.NotFoundException;
import com.example.fulldev.model.Spu;
import com.example.fulldev.service.SpuService;
import com.example.fulldev.util.CommonUtil;
import com.example.fulldev.vo.PagingDozer;
import com.example.fulldev.vo.SpuSimplifyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/spu")
@Validated
public class SpuController {

    @Autowired
    private SpuService spuService;

    @RequestMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @Positive Long id) {
        Spu spu = spuService.getSpuById(id);
        if (null == spu) {
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/id/{id}/simplify")
    public SpuSimplifyVO getSimplifySpu(@PathVariable @Positive Long id) {
        Spu spu = spuService.getSpuById(id);
        SpuSimplifyVO vo = new SpuSimplifyVO();
        BeanUtils.copyProperties(spu, vo);
        return vo;
    }

    @RequestMapping("/latest")
    public PagingDozer getLatestSpuList(@RequestParam(name = "start",defaultValue = "0") Integer start,
                                        @RequestParam(name = "count",defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = spuService.getlatestPagingspu(pageCounter.getPage(), pageCounter.getCount());
        return new PagingDozer(page, SpuSimplifyVO.class);
    }

    @RequestMapping("/by/category/{id}")
    public PagingDozer getByCategoryId(@PathVariable(name = "id") @Positive(message = "{id.positive}") Long id,
                                       @RequestParam(name = "is_root",defaultValue = "false") Boolean isRoot,
                                       @RequestParam(name = "start",defaultValue = "0") Integer start,
                                       @RequestParam(name = "count",defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = spuService.getByCategory(id, isRoot, start, count);
        return new PagingDozer(page, SpuSimplifyVO.class);
    }
}
