package ru.frolov.optica3.service;

import org.springframework.stereotype.Service;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.FrameContainer;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PaginationService {

    public List<FrameContainer> getPageContent(List<FrameContainer> list,
                                               int pageNumber,
                                               int pageSize) {

        System.out.println(
                "_____________pagination getPageContent() list.size:" + list.size() +
                "; pageNumber:" + pageNumber +
                "; pageSize:" + pageSize);

        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());

        if (startIndex >= endIndex || startIndex < 0) {
            // Page number out of range or no data for the page
            return new LinkedList<>();
        }

        return list.subList(startIndex, endIndex);
    }

    public int totalPages(List<?> list,
                          int pageSize) {

        int number = list.size() / pageSize;

        if ((list.size() % pageSize) > 0) {
            number = number + 1;
        }
        return number;
    }



    public int pageNumberOfNew(List<FrameContainer> list,
                               Long id) {
        int totalPages = totalPages(list, Defaults.PAGE_SIZE);


        for (int i = 1; i <= totalPages; i++) {
            Optional<FrameContainer> yeah = getPageContent(list, i, Defaults.PAGE_SIZE).stream()
                    .filter(frameContainer -> frameContainer.getId().equals(id))
                    .findFirst();
            if (yeah.isPresent()) return i;
        }

        return 0;
    }
}
