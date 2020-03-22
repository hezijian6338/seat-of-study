package com.study.room.dto;

/**
 * Created by Macbook on 2020/3/21.
 */
public class FootprintDTO {

    private String userId;

    /**
     * 自习室编号
     */
    private String roomNumber;

    /**
     * 由  row + _ + col 组成
     */
    private String seatsNumber;

    /**
     * 存放时间戳差
     */
    private String stayTime;

    /**
     * 0: 暂时离开(保留90分钟); 1: 正常坐下(时间选择); -1: 正常离开(结束计时)
     */
    private Integer status;

    /**
     * 心情标签 (数组, 用 ,分隔)
     */
    private String momentTag;

    /**
     * 坐下时间
     */
//        private String createdTime;

    /**
     * 更新时间
     */
//        private String updatedTime;

    /**
     * @return id
     */
//    public String getId() {
//        return id;
//    }

    /**
     * @param id
     */
//    public void setId(String id) {
//        this.id = id;
//    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取自习室编号
     *
     * @return room_number - 自习室编号
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * 设置自习室编号
     *
     * @param roomNumber 自习室编号
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * 获取由  row + _ + col 组成
     *
     * @return seats_number - 由  row + _ + col 组成
     */
    public String getSeatsNumber() {
        return seatsNumber;
    }

    /**
     * 设置由  row + _ + col 组成
     *
     * @param seatsNumber 由  row + _ + col 组成
     */
    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    /**
     * 获取存放时间戳差
     *
     * @return stay_time - 存放时间戳差
     */
    public String getStayTime() {
        return stayTime;
    }

    /**
     * 设置存放时间戳差
     *
     * @param stayTime 存放时间戳差
     */
    public void setStayTime(String stayTime) {
        this.stayTime = stayTime;
    }

    /**
     * 获取0: 暂时离开(保留90分钟); 1: 正常坐下(时间选择); -1: 正常离开(结束计时)
     *
     * @return status - 0: 暂时离开(保留90分钟); 1: 正常坐下(时间选择); -1: 正常离开(结束计时)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0: 暂时离开(保留90分钟); 1: 正常坐下(时间选择); -1: 正常离开(结束计时)
     *
     * @param status 0: 暂时离开(保留90分钟); 1: 正常坐下(时间选择); -1: 正常离开(结束计时)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取心情标签 (数组, 用 ,分隔)
     *
     * @return montent_tag - 心情标签 (数组, 用 ,分隔)
     */
    public String getMontentTag() {
        return momentTag;
    }

    /**
     * 设置心情标签 (数组, 用 ,分隔)
     *
     * @param montentTag 心情标签 (数组, 用 ,分隔)
     */
    public void setMontentTag(String montentTag) {
        this.momentTag = montentTag;
    }

    /**
     * 获取坐下时间
     *
     * @return created_time - 坐下时间
     */
//        public String getCreatedTime() {
//            return createdTime;
//        }

    /**
     * 设置坐下时间
     *
     * @param createdTime 坐下时间
     */
//        public void setCreatedTime(String createdTime) {
//            this.createdTime = createdTime;
//        }

    /**
     * 获取更新时间
     *
     * @return updated_time - 更新时间
     */
//        public String getUpdatedTime() {
//            return updatedTime;
//        }

    /**
     * 设置更新时间
     *
     * @param updatedTime 更新时间
     */
//        public void setUpdatedTime(String updatedTime) {
//            this.updatedTime = updatedTime;
//        }
}
